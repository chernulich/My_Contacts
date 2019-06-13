package com.telran.contacts.service.address;

import com.telran.contacts.model.dto.ContactDTO;
import com.telran.contacts.model.entity.Address;
import com.telran.contacts.model.entity.User;

import java.util.List;

public interface AddressService {

    List<Address> getAllByUser(User user);

    void addAddresses(ContactDTO contactDTO, User user);

    void updateAddresses(ContactDTO contactDTO, User user);

    List<Address> getAddressesByUser(User user);

    void deleteAll(User user);
}
