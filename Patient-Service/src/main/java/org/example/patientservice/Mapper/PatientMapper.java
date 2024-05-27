package org.example.patientservice.Mapper;

import org.example.patientservice.Model.Dto.PatientCreateDto;
import org.example.patientservice.Model.Dto.PatientDto;
import org.example.patientservice.Model.Dto.PatientFilterDto;
import org.example.patientservice.Model.Dto.PatientUpdateDto;
import org.example.patientservice.Model.Entity.Patient;
import org.example.patientservice.Model.Payload.PatientRequestFilterDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.WARN)
public interface PatientMapper {
    PatientDto patientToPatientDto(Patient patient);
    Patient patientDtoToPatient(PatientDto patientDto);
    Patient patientUpdateDtoToPatient(PatientUpdateDto patientUpdateDto);
    Patient patientCreateDtoToPatient(PatientCreateDto patientCreateD);
    Patient PatientDtoToPatient(PatientDto patientDto);
    PatientFilterDto PatientRequestFilterDtoToPatientFilterDto(PatientRequestFilterDto patientRequestFilterDto);

}
