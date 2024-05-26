package org.example.reportservice.Model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.reportservice.Model.Enum.TestStatus;

import java.util.Date;
@Entity
@Table(name = "Test_Report")
public class TestReport {
  @Id
  @Getter
  @Setter
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;
  @Column(name = "patient_id")
  @Getter
  @Setter
  private String patientId;
  @Column(name = "test_type")
  @Getter
  @Setter
  private String testType;
  @Column(name = "test_result")
  @Getter
  @Setter
  private String testResult;
  @Column(name = "test_date")
  @Getter
  @Setter
  private Date testDate;
  @Column(name = "status")
  @Getter
  @Setter
  @Enumerated(EnumType.STRING)
  private TestStatus status;


}