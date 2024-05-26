package org.example.patientservice.Service;

import org.example.patientservice.Model.Patient;
import org.example.patientservice.Repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

  @Autowired
  private PatientRepository patientRepository;

  public Optional<Patient> getPatientDetails(String id) {
    return patientRepository.findById(id);
  }
  public Patient createPatient(Patient patient) {
    return patientRepository.save(patient);
  }
  public List<Patient> getAllPatient() {
    return patientRepository.findAll();
  }
}