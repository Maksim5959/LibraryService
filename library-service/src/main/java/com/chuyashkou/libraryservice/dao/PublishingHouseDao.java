package com.chuyashkou.libraryservice.dao;


import com.chuyashkou.libraryservice.mapper.PublishingHouseRowMapper;
import com.chuyashkou.libraryservice.model.PublishingHouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PublishingHouseDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public boolean addPublishingHouse(PublishingHouse publishingHouse) throws EmptyResultDataAccessException {
        return jdbcTemplate.update("INSERT INTO PUBLISHING_HOUSES (publishing_houses.title, publishing_houses.address) " +
                "VALUES (?,?)", publishingHouse.getTitle(), publishingHouse.getAddress()) > 0;
    }

    public PublishingHouse getPublishingHouse(PublishingHouse publishingHouse) throws EmptyResultDataAccessException {
        return jdbcTemplate.queryForObject("SELECT id FROM publishing_houses where title = ? and address =?",
                new BeanPropertyRowMapper<>(PublishingHouse.class), publishingHouse.getTitle(), publishingHouse.getAddress());
    }

    public PublishingHouse getPublishingHouseById(Long id) {
        return jdbcTemplate.queryForObject("SELECT title, address FROM publishing_houses where publishing_houses.id = ?  ",
                new BeanPropertyRowMapper<>(PublishingHouse.class), id);
    }

    public List<PublishingHouse> getAllPublishingHouses() {
        return jdbcTemplate.query("SELECT * FROM publishing_houses", new PublishingHouseRowMapper());
    }

    public boolean deletePublishingHouseById(Long id) {
        return jdbcTemplate.update("DELETE FROM publishing_houses where publishing_houses.id = ? ", id) > 0;
    }

    public boolean updatePublishingHouseById(PublishingHouse publishingHouse) {
        return jdbcTemplate.update("UPDATE publishing_houses SET publishing_houses.title = ?, publishing_houses.address = ? " +
                        " where publishing_houses.id = ?",
                publishingHouse.getTitle(), publishingHouse.getAddress(), publishingHouse.getId()) > 0;
    }

}
