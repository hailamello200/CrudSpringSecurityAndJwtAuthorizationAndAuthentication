package com.spring.demo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AddressDto {

    private Long idAddress;
    private String publicPlace;
    private String neighborhood;
    private String zipCode;
    private String city;
    private String state;
}
