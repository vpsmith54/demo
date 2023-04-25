package com.vpsmith.demo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AddressRowMapper implements RowMapper<Address> {

    @Override
    public Address mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        String addressLine1 = resultSet.getString("address_line1");
        String addressLine2 = resultSet.getString("address_line2");
        String city = resultSet.getString("city");
        String state = resultSet.getString("state");
        String zip = resultSet.getString("zip");
        return Address.builder(id, name, addressLine1, addressLine2, city, state, zip);

    }

}
