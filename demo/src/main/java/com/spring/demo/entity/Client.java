package com.spring.demo.entity;

import java.sql.Timestamp;
import java.util.Date;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "client")
@Getter
@Setter
@Builder
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long idClient;

    @Column(name = "name_client")
    private String nameClient;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "social_security_number")
    private String socialSecurityNumber;

    @Column(name = "date_of_creation")
    private Timestamp dateOfCreation;
}
