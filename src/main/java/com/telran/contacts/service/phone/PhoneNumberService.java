package com.telran.contacts.service.phone;

import com.telran.contacts.model.dto.ContactDTO;
import com.telran.contacts.model.entity.PhoneNumber;
import com.telran.contacts.model.entity.User;

import java.util.List;

public interface PhoneNumberService {

    List<PhoneNumber> getAllByUser(User user);

    void addPhoneNumbers(ContactDTO contactDTO, User user);

    void updatePhoneNumbers(ContactDTO contactDTO, User user);

    List<String> getPhoneNumberNames(User user);

    void deleteAll(User user);
}
