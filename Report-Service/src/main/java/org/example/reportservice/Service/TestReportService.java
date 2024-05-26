package org.example.reportservice.Service;

import org.example.reportservice.Model.Enum.TestStatus;
import org.example.reportservice.Model.entity.TestReport;
import org.example.reportservice.Payload.Request.TestResult;
import org.example.reportservice.Repository.TestReportRepository;
//import org.example.reportservice.feign.PatientClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TestReportService {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(TestReportService.class);
    @Autowired
    private TestReportRepository testReportRepository;

    public TestReport saveTestReport(TestReport testReport){
        logger.info("calling saveTestReport with testReport " + testReport);
        return testReportRepository.save(testReport);
    }
    public Optional<TestReport> getTestReportById(String id){
        logger.info("calling getTestReportById with id " + id);
        return testReportRepository.findById(id);
    }
    public List<TestReport> getTestReportByPatientId(String id){
        logger.info("calling getTestReportById with patient id " + id);
        return testReportRepository.findTestReportByPatientId(id);
    }
    public void changeReportStatusToCompleted(String id, TestResult testResult){
        logger.info("calling changeReportStatusToCompleted with  id " + id);
        Optional<TestReport> optionalTestReport = testReportRepository.findById(id);
        if(optionalTestReport.isPresent()){
            TestReport testReport = optionalTestReport.get();
            testReport.setStatus(TestStatus.COMPLETED);
            testReport.setTestResult(testResult.getTestResult());
            testReportRepository.save(testReport);

        }
        else{
            logger.error("TestReport with id " + id + " not found");
        }
    }
}
