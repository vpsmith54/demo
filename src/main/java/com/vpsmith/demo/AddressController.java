package com.vpsmith.demo;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/address")
public class AddressController {

    private final AddressService service;

    public AddressController(AddressService service) {
        this.service = service;
    }

    @GetMapping
    public List<AddressDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("{id}")
    public AddressDTO findById(@PathVariable Long id) {
        Optional<AddressDTO> address = service.findById(id);
        return address.orElseThrow(() -> new ResponseStatusException(NOT_FOUND,
                "Unable to find the car " + id));
    }

    @PostMapping
    public ResponseEntity<AddressDTO> insert(@RequestBody AddressDTO dto) {
        return new ResponseEntity<>(service.insert(dto), HttpStatus.CREATED);
    }

    @PutMapping(value = "{id}")
    public AddressDTO update(@PathVariable("id") Long id, @RequestBody AddressDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping(value = "{id}")
    public void delete(@PathVariable("id") Long id) {
        service.delete(id);
    }
}