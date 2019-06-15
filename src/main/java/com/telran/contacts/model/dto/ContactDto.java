package com.telran.contacts.model.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ContactDto {

    private Long userId;

    private String fullName;

    private String email;

    private List<String> phoneNumbers;

    private List<AddressDto> addresses;


}
