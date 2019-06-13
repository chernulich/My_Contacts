package com.telran.contacts.service.contact;

import com.telran.contacts.model.dto.ContactDTO;
import com.telran.contacts.model.entity.Address;
import com.telran.contacts.model.entity.User;
import com.telran.contacts.service.address.AddressService;
import com.telran.contacts.service.phone.PhoneNumberService;
import com.telran.contacts.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContactDTOServiceImpl implements ContactDTOService {

    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private PhoneNumberService phoneNumberService;

    public List<ContactDTO> getAll(){
        List<ContactDTO> contacts = new ArrayList<>();
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()){
            return null;
        }
        for (User user : users){
            List<String> numbers = phoneNumberService.getPhoneNumberNames(user);
            List<Address> addressesDto = addressService.getAddressesByUser(user);
            ContactDTO contact = ContactDTO.builder()
                    .userId(user.getUserId())
                    .fullName(user.getFullName())
                    .dateCreated(user.getCreatedDate())
                    .email(user.getEmail())
                    .phoneNumbers(numbers)
                    .addresses(addressesDto).build();
            contacts.add(contact);
        }
        return contacts;
    }

    @Override
    public void addContact(ContactDTO contactDTO) {
        User user = userService.addUser(contactDTO);
        addressService.addAddresses(contactDTO, user);
        phoneNumberService.addPhoneNumbers(contactDTO, user);
    }

    @Override
    public void updateContact(ContactDTO contactDTO) {
        User user = userService.updateUser(contactDTO);
        addressService.updateAddresses(contactDTO, user);
        phoneNumberService.updatePhoneNumbers(contactDTO, user);
    }

    @Override
    public void deleteContact(Long userId) {
        User user = userService.getUserById(userId);
        addressService.deleteAll(user);
        phoneNumberService.deleteAll(user);
        userService.deleteUser(userId);
    }
}
