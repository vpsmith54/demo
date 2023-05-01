package com.vpsmith.demo;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
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

import static com.vpsmith.AddressMapper.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/address")
public class AddressController {

    private final AddressService service;

    public AddressController(AddressService service) {
        this.service = service;
    }

    @GetMapping(produces = { "application/hal+json" })
    public CollectionModel<AddressDTO> findAll() {
        List<Address> addresses = service.findAll();
        List<AddressDTO> dtos = new ArrayList<AddressDTO>();
        for (Address address : addresses) {
            Link selfLink = linkTo(AddressController.class).slash(address.getId()).withSelfRel();
            AddressDTO dto = toDTO(address);
            dto.add(selfLink);
            dtos.add(dto);
        }
        Link aLink = linkTo(methodOn(AddressController.class).findAll()).withSelfRel();
        CollectionModel<AddressDTO> model = CollectionModel.of(dtos, aLink);
        return model;
    }

    @GetMapping(value = "{id}", produces = { "application/hal+json" })
    public EntityModel<AddressDTO> findById(@PathVariable Long id) {
        Optional<Address> address = service.findById(id);
        if (!address.isPresent()) {
            throw new ResponseStatusException(NOT_FOUND,
                    "Unable to find the address " + id);
        }
        Link selfLink = linkTo(AddressController.class).slash(id).withSelfRel();
        AddressDTO dto = toDTO(address.get());
        return EntityModel.of(dto, selfLink);
    }

    @PostMapping(produces = { "application/hal+json" })
    public ResponseEntity<AddressDTO> insert(@RequestBody AddressDTO dto) {
        Address address = service.insert(toEntity(dto));
        AddressDTO returnDto = toDTO(address);
        Link selfLink = linkTo(AddressController.class).slash(returnDto.getId()).withSelfRel();
        returnDto.add(selfLink);
        return new ResponseEntity<>(returnDto, HttpStatus.CREATED);
    }

    @PutMapping(value = "{id}", produces = { "application/hal+json" })
    public EntityModel<AddressDTO> update(@PathVariable("id") Long id, @RequestBody AddressDTO dto) {
        Address address = service.update(id, toEntity(dto));
        Link selfLink = linkTo(AddressController.class).slash(id).withSelfRel();
        AddressDTO returnDto = toDTO(address);
        returnDto.add(selfLink);
        return EntityModel.of(returnDto);
    }

    @DeleteMapping(value = "{id}")
    public void delete(@PathVariable("id") Long id) {
        service.delete(id);
    }
}