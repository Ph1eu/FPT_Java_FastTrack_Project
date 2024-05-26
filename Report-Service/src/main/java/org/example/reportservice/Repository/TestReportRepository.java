package org.example.reportservice.Repository;

import org.example.reportservice.Model.entity.TestReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestReportRepository extends JpaRepository<TestReport,String> {
    List<TestReport> findTestReportByPatientId(String patientId);
}
