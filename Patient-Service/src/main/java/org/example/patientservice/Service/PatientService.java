package org.example.patientservice.Service;

import org.example.patientservice.Model.Dto.PatientCreateDto;
import org.example.patientservice.Model.Dto.PatientFilterDto;
import org.example.patientservice.Model.Dto.PatientPageDto;
import org.example.patientservice.Model.Dto.PatientUpdateDto;
import org.example.patientservice.Model.Entity.Patient;

public interface PatientService {
    Patient getById(String id);
    Patient create(PatientCreateDto dto);
    Patient update(String id, PatientUpdateDto dto);

    PatientPageDto list(PatientFilterDto dto);
}
