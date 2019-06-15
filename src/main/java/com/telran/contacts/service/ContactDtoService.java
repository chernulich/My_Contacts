package com.telran.contacts.service;

import com.telran.contacts.model.dto.ContactDto;

import java.util.List;

public interface ContactDtoService {

    List<ContactDto> getAll();

    void addContact(ContactDto contactDTO);

    void updateContact(ContactDto contactDTO);

    void deleteContact(Long userId);

    ContactDto searchByPhoneNumber(String phoneNumber);

    List<ContactDto> searchByFullName(String fullName);

    ContactDto getContact(Long id);
}
