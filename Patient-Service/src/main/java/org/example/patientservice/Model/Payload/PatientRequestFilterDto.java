package org.example.patientservice.Model.Payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PatientRequestFilterDto {
    private String first_name;
    private String last_name;
    private String dob;
}
