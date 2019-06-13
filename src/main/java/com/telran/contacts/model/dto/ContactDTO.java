package com.telran.contacts.model.dto;

import com.telran.contacts.model.entity.Address;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ContactDTO {

    private Long userId;

    private String fullName;

    private LocalDateTime dateCreated;

    private String email;

    private List<String> phoneNumbers;

    private List<Address> addresses;


}
