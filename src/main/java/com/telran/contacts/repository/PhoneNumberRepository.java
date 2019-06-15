package com.telran.contacts.repository;

import com.telran.contacts.model.entity.PhoneNumber;
import com.telran.contacts.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhoneNumberRepository<deleteAllByUser> extends JpaRepository<PhoneNumber, Long> {

    List<PhoneNumber> findAllByUser(User user);

    void deleteAllByUser(User user);

    PhoneNumber findPhoneNumberByPhoneNumber(String number);


}
