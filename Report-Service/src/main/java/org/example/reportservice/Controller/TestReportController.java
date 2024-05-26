package org.example.reportservice.Controller;

import org.example.reportservice.Model.Enum.TestStatus;
import org.example.reportservice.Model.entity.TestReport;
import org.example.reportservice.Payload.Request.TestResult;
import org.example.reportservice.Payload.Response.Patient;
import org.example.reportservice.Payload.Response.ReportResponse;
import org.example.reportservice.Service.TestReportService;
//import org.example.reportservice.feign.PatientClient;
import org.example.reportservice.feign.PatientClient;
import org.example.reportservice.mqtt.MqttPublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reports")
public class TestReportController {

    @Autowired
    private TestReportService testReportService;
    @Autowired
    private MqttPublisherService mqttPublisherService;
    @Autowired
    private PatientClient patientClient;
    /**
     * this method get report by report id, note : post man samples is included in project
     */

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<ReportResponse> getReportById(@RequestParam("id") String id){
        Optional<TestReport> report = testReportService.getTestReportById(id);
        if(report.isPresent()){
            Patient patient =patientClient.getPatient(report.get().getPatientId());
            ReportResponse reportResponse = new ReportResponse();
            reportResponse.setTestReports(List.of(report.get()));
            reportResponse.setPatient(patient);
            return ResponseEntity.ok(reportResponse);
        }
        return ResponseEntity.notFound().build();
    }
    /**
     * this method get report by patient id , note : post man samples is included in project
     */

    @RequestMapping(method = RequestMethod.GET,value = "/patient")
    public ResponseEntity<ReportResponse> getReportByPatientId(@RequestParam("id") String id){
        List<TestReport> report = testReportService.getTestReportByPatientId(id);
        if(!report.isEmpty()){
            Patient patient =patientClient.getPatient(id);
            ReportResponse reportResponse = new ReportResponse();
            reportResponse.setTestReports(report);
            reportResponse.setPatient(patient);
            return ResponseEntity.ok(reportResponse);
        }
        return ResponseEntity.notFound().build();

    }

    /**
     * this method create report , note : post man samples is included in project
     * @param testReport
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<TestReport> createReport(@RequestBody TestReport testReport){
        return ResponseEntity.ok(testReportService.saveTestReport(testReport));
    }

    /**
     *
     * this controller method change test status to completed and update result and publish message to patient service
     * @param id
     * @param testResult
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "/complete")
    public ResponseEntity<Object> changeReportStatusToComplete(@RequestParam("id") String id, @RequestBody TestResult testResult){
        Optional<TestReport> testReportOptional = testReportService.getTestReportById(id);
        if (testReportOptional.isPresent()){
            TestReport testReport = testReportOptional.get();
            if(testReport.getStatus() == TestStatus.COMPLETED){
                return ResponseEntity.ok("report hash already completed");
            }
            testReportService.changeReportStatusToCompleted(id,testResult);
            String patient_id = testReport.getPatientId();
            String messageString = patient_id + ": " + id;
            mqttPublisherService.publish("report/completed", messageString);
            return ResponseEntity.ok(testReport);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
