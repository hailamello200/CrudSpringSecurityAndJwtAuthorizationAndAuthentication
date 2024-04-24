package com.spring.demo.dto;

import java.sql.Timestamp;
import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ClientDto {

    private Long idClient;
    private String nameClient;
    private Date dateOfBirth;
    private String socialSecurityNumber;
    private Timestamp dateOfCreation;
}
