package org.example.reportservice.Payload.Response;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Data
public class Patient {
    private String id;
    private String first_name;
    private String last_name;
    private LocalDate dob;
}
