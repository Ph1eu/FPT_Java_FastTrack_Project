package org.example.patientservice.Model.Dto;

import lombok.Data;

import java.util.Date;


@Data
public class PatientCreateDto {
    private String first_name;
    private String last_name;
    private Date dob;
}
