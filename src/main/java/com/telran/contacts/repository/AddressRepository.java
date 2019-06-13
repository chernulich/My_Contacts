package com.telran.contacts.repository;

import com.telran.contacts.model.entity.Address;
import com.telran.contacts.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findAllByUser(User user);

    void deleteAllByUser(User user);
}
