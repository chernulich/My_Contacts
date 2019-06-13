package com.telran.contacts.service.address;

import com.telran.contacts.model.dto.ContactDTO;
import com.telran.contacts.model.entity.Address;
import com.telran.contacts.model.entity.User;
import com.telran.contacts.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Address> getAllByUser(User user) {
        return addressRepository.findAllByUser(user);
    }

    @Override
    @Transactional
    public void addAddresses(ContactDTO contactDTO, User user) {
        for (Address address : contactDTO.getAddresses()) {
             address.setUser(user);
             address.setCreatedDate(LocalDateTime.now());
             addressRepository.save(address);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Address> getAddressesByUser(User user) {
        List<Address> addressesDto = new ArrayList<>();
        for (Address address : addressRepository.findAllByUser(user)) {
            Address addressDto = Address.builder()
                    .addressId(address.getAddressId())
                    .country(address.getCountry())
                    .city(address.getCity())
                    .street(address.getStreet())
                    .houseNumber(address.getHouseNumber())
                    .apartment(address.getApartment()).build();
            addressesDto.add(addressDto);
        }
        return addressesDto;
    }

    @Override
    @Transactional
    public void deleteAll(User user) {
        addressRepository.deleteAllByUser(user);
    }

    @Override
    @Transactional                                                        //TODO
    public void updateAddresses(ContactDTO contactDTO, User user) {
        List<Address> addresses = contactDTO.getAddresses();
        for (Address address : addresses){
            Address DBAddress = addressRepository.findById(address.getAddressId()).orElse(null);
            if (DBAddress == null){
                Address.builder()
                        .country(address.getCountry())
                        .city(address.getCity())
                        .street(address.getStreet())
                        .houseNumber(address.getHouseNumber())
                        .apartment(address.getApartment())
                        .user(user).build();
                addressRepository.save(address);
            }
            DBAddress.setCountry(address.getCountry());
            DBAddress.setCreatedDate(LocalDateTime.now());
            DBAddress.setCity(address.getCity());
            DBAddress.setStreet(address.getStreet());
            DBAddress.setHouseNumber(address.getHouseNumber());
            DBAddress.setApartment(address.getApartment());
        }
    }
}
