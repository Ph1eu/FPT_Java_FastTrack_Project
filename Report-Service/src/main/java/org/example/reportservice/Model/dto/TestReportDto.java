package org.example.reportservice.Model.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.example.reportservice.Model.Enum.TestStatus;

import java.util.Date;
@Data
@AllArgsConstructor
public class TestReportDto {
    private String id;
    private String patientId;
    private String testType;
    private String testResult;
    private Date testDate;
    private TestStatus testStatus;
}
