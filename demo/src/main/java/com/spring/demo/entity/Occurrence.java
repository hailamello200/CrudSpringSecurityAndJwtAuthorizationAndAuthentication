package com.spring.demo.entity;

import java.util.Date;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "occurrence")
@Getter
@Setter
@Builder
public class Occurrence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long idOccurrence;

    @Column(name = "id_client")
    private int idClient;

    @Column(name = "id_address")
    private int idAddress;

    @Column(name = "date_occurrence")
    private Date dateOccurrence;

    @Column(name = "satus")
    private String statusOccurrence;
}
