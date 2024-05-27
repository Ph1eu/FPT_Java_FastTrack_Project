package org.example.patientservice.Model.Dto;

import lombok.Data;

@Data
public class PatientFilterDto {
    private String first_name;
    private String last_name;
    private String dob;
    private int page;
    private int size;
}
