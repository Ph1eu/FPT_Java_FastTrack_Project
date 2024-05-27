package org.example.patientservice.Model.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name="Patient")
public class Patient {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Setter
    @Getter
    @Column(name = "first_name")
    private String first_name;
    @Setter
    @Getter
    @Column(name = "last_name")
    private String last_name;
    @Setter
    @Getter
    @Column(name = "dob")
    private Date dob;
}
