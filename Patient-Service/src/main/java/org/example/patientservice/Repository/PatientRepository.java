package org.example.patientservice.Repository;

import org.example.patientservice.Model.Entity.Patient;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Date;

public interface PatientRepository extends JpaRepository<Patient, String>, JpaSpecificationExecutor<Patient> {
    static Specification<Patient> hasFirstName(String firstName) {
        return (root, query, builder) -> builder.equal(root.get("firstName"), firstName);
    }
     static Specification<Patient> hasLastName(String lastName) {
        return (root, query, builder) -> builder.equal(root.get("lastName"), lastName);
    }
    static Specification<Patient> hasDob(Date date) {
        return (root, query, builder) -> builder.equal(root.get("dob"), date);
    }
}
