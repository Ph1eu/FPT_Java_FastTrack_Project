package org.example.reportservice.SpringBatch.Processor;

import org.example.reportservice.Model.dto.TestReportDto;
import org.example.reportservice.Model.entity.TestReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class TestReportProcessor implements ItemProcessor<TestReport, TestReportDto> {
    // import logger
    private static final Logger logger = LoggerFactory.getLogger(TestReportProcessor.class);
    @Override
    public TestReportDto process(TestReport item) throws Exception {
        logger.info("Processing " + item.toString());
        return new TestReportDto(item.getId(), item.getPatientId(),item.getTestType(),item.getTestResult(),item.getTestDate(),item.getStatus());
    }
}
