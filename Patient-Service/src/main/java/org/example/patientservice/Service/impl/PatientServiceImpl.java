package org.example.patientservice.Service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.example.patientservice.Mapper.PatientMapper;
import org.example.patientservice.Model.Dto.PatientCreateDto;
import org.example.patientservice.Model.Dto.PatientFilterDto;
import org.example.patientservice.Model.Dto.PatientPageDto;
import org.example.patientservice.Model.Dto.PatientUpdateDto;
import org.example.patientservice.Model.Entity.Patient;
import org.example.patientservice.Repository.PatientRepository;
import org.example.patientservice.Service.PatientService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Optional;

import static org.example.patientservice.Utils.ObjectUtils.getNullPropertyNames;

@Service
public class PatientServiceImpl implements PatientService {

  @Autowired
  private PatientRepository patientRepository;
  @Autowired
  private PatientMapper patientMapper;

  @Override
  public Patient getById(String id) {
    return patientRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Patient not found")
    );
  }
  @Override
  public Patient create(PatientCreateDto dto) {
    Patient patient = patientMapper.patientCreateDtoToPatient(dto);
    return patientRepository.save(patient);
  }

  @Override
  public Patient update(String id, PatientUpdateDto dto) {
    Patient patient = getById(id);
    BeanUtils.copyProperties(dto, patient, getNullPropertyNames(dto));
    return patientRepository.save(patient);
  }

  @Override
  public PatientPageDto list(PatientFilterDto dto) {
    Specification<Patient> spec = Specification.where(null);
    spec = spec.and(Optional.ofNullable(dto.getFirst_name()).map(PatientRepository::hasFirstName).orElse(null));
    spec = spec.and(Optional.ofNullable(dto.getLast_name()).map(PatientRepository::hasLastName).orElse(null));
    spec = spec.and(Optional.ofNullable(dto.getDob()).map(dob -> PatientRepository.hasDob(Date.valueOf(dob))).orElse(null));
    Pageable pageable = Pageable.ofSize(dto.getSize());
    Page<Patient> patients= patientRepository.findAll(spec, pageable);
    return new PatientPageDto(patients.getTotalElements(),patients.getTotalPages(),patients.getContent());
  }
}