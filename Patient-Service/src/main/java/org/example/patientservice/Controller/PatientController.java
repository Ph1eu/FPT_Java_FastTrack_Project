package org.example.patientservice.Controller;

import org.example.patientservice.Mapper.PatientMapper;
import org.example.patientservice.Model.Dto.PatientCreateDto;
import org.example.patientservice.Model.Dto.PatientDto;
import org.example.patientservice.Model.Dto.PatientFilterDto;
import org.example.patientservice.Model.Entity.Patient;
import org.example.patientservice.Model.Payload.PatientRequestFilterDto;
import org.example.patientservice.Service.impl.PatientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/patient")
public class PatientController {
    @Autowired
    private PatientServiceImpl patientServiceImpl;
    @Autowired
    private PatientMapper mapper;
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> getPatientById(@RequestParam("id") String id){
        Patient patient = patientServiceImpl.getById(id);
        if(patient != null){
            PatientDto patientDto = mapper.patientToPatientDto(patient);
            return ResponseEntity.ok(patientDto);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    @RequestMapping(method = RequestMethod.POST,value = "/list")
    public ResponseEntity<Object> listPatients(@RequestBody PatientRequestFilterDto requestFilterDto,@RequestParam("page" ) int page,@RequestParam("size") int size){
        PatientFilterDto filterDto = mapper.PatientRequestFilterDtoToPatientFilterDto(requestFilterDto);
        filterDto.setSize(size);
        filterDto.setPage(page);
        return ResponseEntity.ok(patientServiceImpl.list(filterDto));
    }
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> createPatient(@RequestBody PatientCreateDto patientCreateDto){
        return ResponseEntity.ok(patientServiceImpl.create(patientCreateDto));
    }
}
