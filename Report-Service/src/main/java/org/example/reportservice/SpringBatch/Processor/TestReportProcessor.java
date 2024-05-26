package org.example.reportservice.SpringBatch.Processor;

import org.example.reportservice.Model.dto.TestReportDto;
import org.example.reportservice.Model.entity.TestReport;
import org.springframework.batch.item.ItemProcessor;

public class TestReportProcessor implements ItemProcessor<TestReport, TestReportDto> {
    @Override
    public TestReportDto process(TestReport item) throws Exception {
        return new TestReportDto(item.getId(), item.getPatientId(),item.getTestType(),item.getTestResult(),item.getTestDate(),item.getStatus());
    }
}
