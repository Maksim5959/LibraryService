package com.chuyashkou.libraryservice.mapper;

import com.chuyashkou.libraryservice.model.Client;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientRowMapper implements RowMapper<Client> {
    @Override
    public Client mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Client client = new Client();
        client.setId(resultSet.getLong(1));
        client.setFirstname(resultSet.getString(2));
        client.setLastname(resultSet.getString(3));
        client.setBirthday(resultSet.getString(4));
        return client;
    }

}
