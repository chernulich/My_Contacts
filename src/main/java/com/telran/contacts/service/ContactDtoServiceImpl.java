package com.telran.contacts.service;

import com.telran.contacts.exception.DuplicateRuntimeException;
import com.telran.contacts.exception.NotFoundRuntimeException;
import com.telran.contacts.model.dto.AddressDto;
import com.telran.contacts.model.dto.ContactDto;
import com.telran.contacts.model.entity.Address;
import com.telran.contacts.model.entity.PhoneNumber;
import com.telran.contacts.model.entity.User;
import com.telran.contacts.repository.AddressRepository;
import com.telran.contacts.repository.PhoneNumberRepository;
import com.telran.contacts.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactDtoServiceImpl implements ContactDtoService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PhoneNumberRepository phoneNumberRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ContactDto> getAll() {
        return userRepository.findAll().stream()
                .map(user -> getContact(user.getUserId()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void addContact(ContactDto contactDTO) {
        List<String> numbersDto = contactDTO.getPhoneNumbers();
        numbersDto.forEach(number -> {
            PhoneNumber phoneNumber = phoneNumberRepository.findPhoneNumberByPhoneNumber(number);
            if (phoneNumber != null){
                throw new DuplicateRuntimeException(number + " already exist!");
            }
        });
        String email = contactDTO.getEmail();
        if (userRepository.findUserByEmail(email) != null) {
            throw new DuplicateRuntimeException(email + " already exist");
        }
        User user = User.builder()
                .fullName(contactDTO.getFullName())
                .email(contactDTO.getEmail())
                .createdDate(LocalDateTime.now()).build();
        userRepository.save(user);
        List<String> numbers = contactDTO.getPhoneNumbers();
        List<PhoneNumber> phoneNumbers = numbers.stream()
                .map(number -> PhoneNumber
                        .builder()
                        .createdDate(LocalDateTime.now())
                        .phoneNumber(number)
                        .user(user).build())
                .collect(Collectors.toList());
        phoneNumberRepository.saveAll(phoneNumbers);
        List<Address> addresses = convDtoToAddress(contactDTO.getAddresses(), user);
        addressRepository.saveAll(addresses);
    }

    @Override                                  //TODO
    @Transactional
    public void updateContact(ContactDto contactDto) {
        User userByEmail = userRepository.findUserByEmail(contactDto.getEmail());
        User user = userRepository.findById(contactDto.getUserId()).orElseThrow(() ->
                new NotFoundRuntimeException("User is not found"));
        if (contactDto.getUserId() != userByEmail.getUserId()){
            throw new DuplicateRuntimeException(contactDto.getEmail() + " already exist!");
        }
        user.setFullName(contactDto.getFullName());
        user.setEmail(contactDto.getEmail());
        updateAddresses(contactDto, user);
        phoneNumberRepository.deleteAllByUser(user);
        List<PhoneNumber> numbers = contactDto.getPhoneNumbers().stream()
                .map(number -> PhoneNumber.builder()
                          .phoneNumber(number)
                          .user(user)
                          .createdDate(LocalDateTime.now()).build()
               ).collect(Collectors.toList());
        phoneNumberRepository.saveAll(numbers);
    }

    private void updateAddresses(ContactDto contactDto, User user) {
        List<AddressDto> addressDtoList = contactDto.getAddresses();
        addressDtoList.forEach(addressDto -> {
            Address address = addressRepository.findById(addressDto.getAddressId()).orElse(null);
            if (address == null) {
                addressRepository.save(Address.builder()
                        .country(addressDto.getCountry())
                        .city(addressDto.getCity())
                        .street(addressDto.getStreet())
                        .houseNumber(addressDto.getHouseNumber())
                        .apartment(addressDto.getApartment())
                        .createdDate(LocalDateTime.now())
                        .user(user).build());
            }
            address.setApartment(addressDto.getApartment());
            address.setHouseNumber(addressDto.getHouseNumber());
            address.setStreet(addressDto.getStreet());
            address.setCity(addressDto.getCity());
            address.setCountry(addressDto.getCountry());
        });
    }

    @Override
    @Transactional
    public void deleteContact(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundRuntimeException("User is not exist by id : " + userId));
        addressRepository.deleteAllByUser(user);
        phoneNumberRepository.deleteAllByUser(user);
        userRepository.deleteById(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public ContactDto searchByPhoneNumber(String phoneNumber) {
        PhoneNumber phone = phoneNumberRepository.findPhoneNumberByPhoneNumber(phoneNumber);
        if (phone == null) {
            throw new NotFoundRuntimeException("User is not exist by phoneNumber : " + phoneNumber);
        }
        return getContact(phone.getUser().getUserId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContactDto> searchByFullName(String fullName) {
        List<User> users = userRepository.findByFullName(fullName);
        if (users == null) {
            return new ArrayList<>();
        }
        return users.stream()
                .map(user -> getContact(user.getUserId()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ContactDto getContact(Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new NotFoundRuntimeException("User is not exist by id : " + id));
        List<String> numbersDto = getNumbers(user);
        List<Address> addresses = addressRepository.findAllByUser(user);

        return ContactDto.builder()
                .userId(user.getUserId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phoneNumbers(numbersDto)
                .addresses(convAddressToDto(addresses)).build();
    }

    private List<String> getNumbers(User user) {
        List<PhoneNumber> numbers = phoneNumberRepository.findAllByUser(user);
        return numbers.stream()
                .map(phoneNumber -> phoneNumber.getPhoneNumber())
                .collect(Collectors.toList());
    }

    public List<AddressDto> convAddressToDto(List<Address> addresses) {
        if (addresses.isEmpty()) {
            return new ArrayList<>();
        }
        return addresses.stream()
                .map(address -> AddressDto.builder()
                        .addressId(address.getAddressId())
                        .country(address.getCountry())
                        .city(address.getCity())
                        .street(address.getStreet())
                        .houseNumber(address.getHouseNumber())
                        .apartment(address.getApartment()).build())
                .collect(Collectors.toList());
    }

    public List<Address> convDtoToAddress(List<AddressDto> addressDtoList, User user) {
        if (addressDtoList.isEmpty()) {
            return new ArrayList<>();
        }
        return addressDtoList.stream()
                .map(addressDto -> Address
                        .builder()
                        .addressId(addressDto.getAddressId())
                        .user(user)
                        .country(addressDto.getCountry())
                        .city(addressDto.getCity())
                        .street(addressDto.getStreet())
                        .houseNumber(addressDto.getHouseNumber())
                        .apartment(addressDto.getApartment())
                        .createdDate(LocalDateTime.now()).build())
                .collect(Collectors.toList());
    }
}

