package org.example.reportservice.SpringBatch.Mapper;

import org.example.reportservice.Model.Enum.TestStatus;
import org.example.reportservice.Model.entity.TestReport;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CustomTestReportRowMapper implements RowMapper<TestReport> {

    @Override
    public TestReport mapRow(ResultSet rs, int rowNum) throws SQLException, SQLException {
        TestReport testReport = new TestReport();
        testReport.setId(rs.getString("id"));
        testReport.setPatientId(rs.getString("patient_id"));
        testReport.setTestType(rs.getString("test_type"));
        testReport.setTestResult(rs.getString("test_result"));
        testReport.setTestDate(rs.getDate("test_date"));
        testReport.setStatus(TestStatus.valueOf(rs.getString("status")));
        return testReport;
    }
}