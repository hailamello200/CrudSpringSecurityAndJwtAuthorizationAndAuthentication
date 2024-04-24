package com.spring.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "address")
@Entity(name = "address")
@Getter
@Setter
@Builder
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long idAddress;

    @Column(name = "public_place")
    private String publicPlace;

    @Column(name = "neighborhood")
    private String neighborhood;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;
}
