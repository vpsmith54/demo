package com.vpsmith.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AddressQueries {

    @Value("${address.query.find.by.id}")
    private String findById;
    @Value("${address.query.delete.by.id}")
    private String deleteById;
    @Value("${address.query.update}")
    private String update;
    @Value("${address.query.find.all}")
    private String findAll;

    public String getFindById() {
        return findById;
    }

    public String getDeleteById() {
        return deleteById;
    }

    public String getUpdate() {
        return update;
    }

    public String getFindAll() {
        return findAll;
    }
}
