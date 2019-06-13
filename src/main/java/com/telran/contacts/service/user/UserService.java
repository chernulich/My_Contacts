package com.telran.contacts.service.user;

import com.telran.contacts.model.dto.ContactDTO;
import com.telran.contacts.model.entity.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User addUser(ContactDTO contactDTO);

    User updateUser(ContactDTO contactDTO);

    void deleteUser(Long userId);

    User getUserById(Long userId);
}
