package org.example.webclient.Payload.Response;

import lombok.Data;
import org.example.webclient.Model.TestReportDto;

import java.util.List;

@Data
public class ReportResponse {
    private List<TestReportDto> testReports;
    private Patient patient;
}
