package com.spring.demo.dto;

import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OccurrenceDto {

    private Long idOccurrence;
    private int idClient;
    private int idAddress;
    private Date dateOccurrence;
    private String statusOccurrence;
}
