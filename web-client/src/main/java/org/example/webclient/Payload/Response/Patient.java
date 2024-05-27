package org.example.webclient.Payload.Response;

import lombok.Data;

import java.time.LocalDate;
@Data
public class Patient {
    private String id;
    private String first_name;
    private String last_name;
    private LocalDate dob;
}
