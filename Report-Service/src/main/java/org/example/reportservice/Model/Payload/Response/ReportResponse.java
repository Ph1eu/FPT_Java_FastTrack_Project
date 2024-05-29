package org.example.reportservice.Model.Payload.Response;

import lombok.Data;
import org.example.reportservice.Model.entity.TestReport;

import java.util.List;

@Data
public class ReportResponse {
    private List<TestReport> testReports;
    private Patient patient;
}
