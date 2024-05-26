package org.example.patientservice.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.IdGeneratorType;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.util.IdGenerator;

import java.time.LocalDate;
import java.util.Set;

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
    private LocalDate dob;
}
