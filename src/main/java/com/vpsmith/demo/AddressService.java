package com.vpsmith.demo;

import org.springframework.stereotype.Service;

import com.vpsmith.AddressMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AddressService {

    private final AddressDAO dao;

    public AddressService(AddressDAO dao) {
        this.dao = dao;
    }

    public List<AddressDTO> findAll() {
        Stream<Address> stream = dao.findAll();
        return stream.map(AddressMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<AddressDTO> findById(Long id) {
        return dao.findBy(id).map(AddressMapper::toDTO);
    }

    public AddressDTO insert(AddressDTO dto) {
        Address address = AddressMapper.toEntity(dto);
        return AddressMapper.toDTO(dao.insert(address));
    }

    public AddressDTO update(Long id, AddressDTO dto) {
        Address address = dao.findBy(id)
                .orElseThrow(() -> new RuntimeException("Address not found with the id " + id));
        address.update(AddressMapper.toEntity(dto));
        dao.update(address);
        return AddressMapper.toDTO(address);
    }

    public void delete(Long id) {
        dao.delete(id);
    }
}