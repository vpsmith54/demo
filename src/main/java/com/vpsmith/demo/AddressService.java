package com.vpsmith.demo;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class AddressService {

    private final AddressDAO dao;

    public AddressService(AddressDAO dao) {
        this.dao = dao;
    }

    public List<Address> findAll() {
        Stream<Address> stream = dao.findAll();
        return stream.toList();
    }

    public Optional<Address> findById(Long id) {
        return dao.findBy(id);
    }

    public Address insert(Address dto) {
        return dao.insert(dto);
    }

    public Address update(Long id, Address dto) {
        Address address = dao.findBy(id)
                .orElseThrow(() -> new RuntimeException("Address not found with the id " + id));
        address.update(dto);
        dao.update(address);
        return address;
    }

    public void delete(Long id) {
        dao.delete(id);
    }
}