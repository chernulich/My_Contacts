package com.telran.contacts.service.contact;

import com.telran.contacts.model.dto.ContactDTO;

import java.util.List;

public interface ContactDTOService {

    List<ContactDTO> getAll();

    void addContact(ContactDTO contactDTO);

    void updateContact(ContactDTO contactDTO);

    void deleteContact(Long userId);
}
