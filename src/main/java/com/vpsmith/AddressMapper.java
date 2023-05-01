package com.vpsmith;

import com.vpsmith.demo.Address;
import com.vpsmith.demo.AddressDTO;

public abstract class AddressMapper {

    public static Address toEntity(AddressDTO dto) {
        return Address.builder(dto.getId(), dto.getName(), dto.getAddressLine1(), dto.getAddressLine2(), dto.getCity(),
                dto.getState(), dto.getZip());
    };

    public static AddressDTO toDTO(Address address) {
        return new AddressDTO(address.getId(), address.getName(), address.getAddressLine1(), address.getAddressLine2(),
                address.getCity(),
                address.getState(), address.getZip());
    }

}