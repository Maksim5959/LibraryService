package com.chuyashkou.libraryservice.mapper;

import com.chuyashkou.libraryservice.model.Accounting;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountingRowMapper implements RowMapper<Accounting> {

    @Override
    public Accounting mapRow(ResultSet resultSet, int i) throws SQLException {
        Accounting accounting = new Accounting();
        accounting.setId(resultSet.getLong(1));
        accounting.setDate(resultSet.getString(2));
        accounting.setStatus(resultSet.getString(3));
        accounting.setBookId(resultSet.getLong(4));
        accounting.setClientId(resultSet.getLong(5));
        accounting.setUserId(resultSet.getLong(6));
        return accounting;
    }
}
