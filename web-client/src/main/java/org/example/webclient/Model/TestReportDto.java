package org.example.webclient.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class TestReportDto {
    private String id;
    private String patientId;
    private String testType;
    private String testResult;
    private Date testDate;
    private String testStatus;
}
