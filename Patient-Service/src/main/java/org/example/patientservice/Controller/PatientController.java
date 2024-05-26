package org.example.patientservice.Controller;

import org.example.patientservice.Model.Patient;
import org.example.patientservice.Service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/patient")
public class PatientController {
    @Autowired
    private PatientService patientService;
    @GetMapping()
    public ResponseEntity<Object> getPatient(@RequestParam("id") String id){
        Optional<Patient> patient = patientService.getPatientDetails(id);
        return patient.<ResponseEntity<Object>>map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/all")
    public ResponseEntity<Object> getAllPatients(){
        return ResponseEntity.ok(patientService.getAllPatient());
    }
    @PostMapping
    public ResponseEntity<Object> createPatient(@RequestBody Patient patient){
        return ResponseEntity.ok(patientService.createPatient(patient));
    }
}
