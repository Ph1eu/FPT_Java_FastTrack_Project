package org.example.reportservice.SpringBatch.Config;

import org.example.reportservice.Model.dto.TestReportDto;
import org.springframework.batch.item.file.transform.FieldExtractor;
import org.springframework.batch.item.file.transform.LineAggregator;

import java.util.Arrays;
import java.util.List;

public class CustomLineAggregator implements LineAggregator<List<TestReportDto>> {

    private final FieldExtractor<TestReportDto> fieldExtractor;

    public CustomLineAggregator(FieldExtractor<TestReportDto> fieldExtractor) {
        this.fieldExtractor = fieldExtractor;
    }


    @Override
    public String aggregate(List<TestReportDto> item) {
        StringBuilder lineBuilder = new StringBuilder();
        for (TestReportDto testReportDto : item) {
            lineBuilder.append(Arrays.toString(fieldExtractor.extract(testReportDto)));
            lineBuilder.append(System.lineSeparator());
        }
        return lineBuilder.toString();
    }
}