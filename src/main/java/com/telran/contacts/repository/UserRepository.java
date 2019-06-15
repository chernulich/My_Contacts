package com.telran.contacts.repository;

import com.telran.contacts.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String email);

    List<User> findByFullName(String fullName);
}
