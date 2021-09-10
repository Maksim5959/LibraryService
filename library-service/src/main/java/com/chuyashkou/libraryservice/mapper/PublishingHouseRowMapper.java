package com.chuyashkou.libraryservice.mapper;

import com.chuyashkou.libraryservice.model.PublishingHouse;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PublishingHouseRowMapper implements RowMapper<PublishingHouse> {

    @Override
    public PublishingHouse mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        PublishingHouse publishingHouse = new PublishingHouse();
        publishingHouse.setId(resultSet.getLong(1));
        publishingHouse.setTitle(resultSet.getString(2));
        publishingHouse.setAddress(resultSet.getString(3));
        return publishingHouse;
    }
}
