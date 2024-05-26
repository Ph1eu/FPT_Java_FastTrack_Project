package org.example.reportservice.feign;

import org.example.reportservice.Payload.Response.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient( name = "patient-client",url = "http://localhost:8080/api/patient")
public interface PatientClient {
    @GetMapping
    Patient getPatient(@RequestParam String id);
}