package com.telran.contacts.controller;

import com.telran.contacts.model.dto.ContactDto;
import com.telran.contacts.service.ContactDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")

public class ContactController {

    @Autowired
    private ContactDtoService contactDtoService;

    @GetMapping("/contacts")
    public List<ContactDto> getAll() {
        return contactDtoService.getAll();
    }

    @PostMapping("/contacts")
    public void addContact(@RequestBody ContactDto contactDTO) {
        contactDtoService.addContact(contactDTO);
    }

    @GetMapping("/contacts/{id}")
    public ContactDto getContact(Long id) {
        return contactDtoService.getContact(id);
    }

    @PutMapping("/contacts")
    public void updateContact(@RequestBody ContactDto contactDTO) {
        contactDtoService.updateContact(contactDTO);
    }

    @DeleteMapping("/contacts/{id}")
    public void deleteContact(@PathVariable(name = "id") Long userId) {
        contactDtoService.deleteContact(userId);
    }

    @GetMapping("/search/phone")
    public ContactDto getContactByPhoneNumber(@RequestParam(name = "number") String phoneNumber) {
        return contactDtoService.searchByPhoneNumber(phoneNumber);
    }

    @GetMapping("/search/fullname")
    public List<ContactDto> getAllContactsByFullName(@RequestParam(name = "name") String fullName) {
        return contactDtoService.searchByFullName(fullName);
    }

}
