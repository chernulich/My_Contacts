package com.telran.contacts.controller;

import com.telran.contacts.model.dto.ContactDTO;
import com.telran.contacts.service.contact.ContactDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")

public class ContactController {

    @Autowired
    private ContactDTOService contactDTOService;

    @GetMapping("/contacts")
    public List<ContactDTO> getAll(){
        return contactDTOService.getAll();
    }

    @PostMapping("/contacts")
    public void addContact(@RequestBody ContactDTO contactDTO){
        contactDTOService.addContact(contactDTO);
    }

    @PutMapping("/contacts")
    public void updateContact(@RequestBody ContactDTO contactDTO){
        contactDTOService.updateContact(contactDTO);
    }

    @DeleteMapping("/contacts/{id}")
    public void deleteContact(@PathVariable(name = "id") Long userId){
        contactDTOService.deleteContact(userId);
    }
}
