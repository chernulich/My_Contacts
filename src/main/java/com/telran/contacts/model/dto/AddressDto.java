package com.telran.contacts.model.dto;


import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDto {

    private Long addressId;

    private String country;

    private String city;

    private String street;

    private String houseNumber;

    private Integer apartment;
}
