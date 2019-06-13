package com.telran.contacts.service.phone;

import com.telran.contacts.model.dto.ContactDTO;
import com.telran.contacts.model.entity.PhoneNumber;
import com.telran.contacts.model.entity.User;
import com.telran.contacts.repository.PhoneNumberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PhoneNumberServiceImpl implements PhoneNumberService {

    @Autowired
    private PhoneNumberRepository phoneNumberRepository;

    @Override
    @Transactional(readOnly = true)
    public List<PhoneNumber> getAllByUser(User user) {
        return phoneNumberRepository.findAllByUser(user);
    }

    @Override
    @Transactional
    public void addPhoneNumbers(ContactDTO contactDTO, User user) {
        List<String> numbers = contactDTO.getPhoneNumbers();
        for (String number : numbers){
            PhoneNumber phoneNumber = PhoneNumber.builder()
                    .phoneNumber(number)
                    .createdDate(LocalDateTime.now())
                    .user(user).build();
            phoneNumberRepository.save(phoneNumber);
        }
    }

    @Override
    @Transactional
    public void updatePhoneNumbers(ContactDTO contactDTO, User user) {
//        List<PhoneNumber> DbNumbers = phoneNumberRepository.findAllByUser(user);
//        phoneNumberRepository.deleteAll(DbNumbers);
        phoneNumberRepository.deleteAllByUser(user);

        List<PhoneNumber> newNumbers = new ArrayList<>();
        List<String> numbers = contactDTO.getPhoneNumbers();
        for (String number : numbers){
            PhoneNumber phoneNumber = PhoneNumber.builder()
                    .phoneNumber(number)
                    .createdDate(LocalDateTime.now())
                    .user(user).build();
            newNumbers.add(phoneNumber);
        }
        phoneNumberRepository.saveAll(newNumbers);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getPhoneNumberNames(User user) {
        List<String> numbersDto = new ArrayList<>();
        List<PhoneNumber> BDNumbers = phoneNumberRepository.findAllByUser(user);
        for (PhoneNumber phone : BDNumbers) {
                String phoneNumber = phone.getPhoneNumber();
            numbersDto.add(phoneNumber);
            }
        return numbersDto;
    }

    @Override
    @Transactional
    public void deleteAll(User user) {
        phoneNumberRepository.deleteAllByUser(user);
    }
}
