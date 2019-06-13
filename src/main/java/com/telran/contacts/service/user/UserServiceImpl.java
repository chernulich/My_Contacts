package com.telran.contacts.service.user;

import com.telran.contacts.model.dto.ContactDTO;
import com.telran.contacts.model.entity.User;
import com.telran.contacts.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User addUser(ContactDTO contactDTO) {
        User user = User.builder()
                .fullName(contactDTO.getFullName())
                .createdDate(LocalDateTime.now())
                .email(contactDTO.getEmail())
                .build();
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User updateUser(ContactDTO contactDTO) {
        Long userId = contactDTO.getUserId();
        User user = userRepository.findById(userId).orElse(null);
        user.setCreatedDate(LocalDateTime.now());
        user.setFullName(contactDTO.getFullName());
        user.setEmail(contactDTO.getEmail());
        return user;
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(Long userId) {
        return userRepository.findById(userId).get();
    }
}
