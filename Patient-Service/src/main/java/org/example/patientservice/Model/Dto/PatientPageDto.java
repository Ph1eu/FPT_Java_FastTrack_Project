package org.example.patientservice.Model.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.patientservice.Model.Entity.Patient;

import java.util.List;
@Data
@AllArgsConstructor
public class PatientPageDto {
    private long totalElement;
    private int totalPage;
    private List<Patient> content;
}
