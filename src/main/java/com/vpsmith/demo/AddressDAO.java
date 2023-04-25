package com.vpsmith.demo;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class AddressDAO {
    private final NamedParameterJdbcTemplate template;
    private final AddressQueries queries;
    private final RowMapper<Address> rowMapper;
    private final SimpleJdbcInsert insert;

    public AddressDAO(NamedParameterJdbcTemplate template, AddressQueries queries) {
        this.template = template;
        this.rowMapper = new AddressRowMapper();
        this.queries = queries;
        this.insert = new SimpleJdbcInsert(template.getJdbcTemplate());
        this.insert.setTableName("address");
        this.insert.usingGeneratedKeyColumns("id");
    }

    @Transactional
    public Address insert(Address address) {
        // Number id = insert.executeAndReturnKey(new
        // BeanPropertySqlParameterSource(car));
        Number id = insert.executeAndReturnKey(address.toMap());
        return findBy(id.longValue()).orElseThrow(() -> new IllegalStateException(""));
    }

    public Optional<Address> findBy(Long id) {
        String sql = queries.getFindById();
        Map<String, Object> parameters = Collections.singletonMap("id", id);
        return template.queryForStream(sql, parameters, rowMapper).findFirst();
    }

    @Transactional
    public boolean delete(Long id) {
        String sql = queries.getDeleteById();
        Map<String, Object> paramMap = Collections.singletonMap("id", id);
        return template.update(sql, paramMap) == 1;
    }

    @Transactional
    public boolean update(Address address) {
        String sql = queries.getUpdate();
        Map<String, Object> paramMap = address.toMap();
        return template.update(sql, paramMap) == 1;
    }

    public Stream<Address> findAll() {
        String sql = queries.getFindAll();
        Map<String, Object> paramMap = new HashMap<>();
        return template.queryForStream(sql, paramMap, rowMapper);
    }
}
