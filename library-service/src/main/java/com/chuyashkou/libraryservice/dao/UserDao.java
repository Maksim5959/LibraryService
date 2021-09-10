package com.chuyashkou.libraryservice.dao;

import com.chuyashkou.libraryservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public boolean addUser(User user) {
        try {
            validateSingUp(user);
            return false;
        } catch (EmptyResultDataAccessException e) {
            return jdbcTemplate.update("INSERT INTO USERS (users.email, users.password, users.firstname,users.lastname,users.role_id) " +
                    "VALUES (?,?,?,?,?)", user.getEmail(), user.getPassword(), user.getFirstname(), user.getLastname(), 2) > 0;
        }
    }


    public User getUser(User user) throws EmptyResultDataAccessException {
        return jdbcTemplate.queryForObject("SELECT id, firstname, lastname, email, password, role_id FROM users where users.email = ? and users.password = ?  ",
                new BeanPropertyRowMapper<>(User.class), user.getEmail(), user.getPassword());
    }

    public User getUserById(Long id) {
        return jdbcTemplate.queryForObject("SELECT email, firstname, lastname FROM users where users.id = ?  ",
                new BeanPropertyRowMapper<>(User.class), id);
    }

    public User validateSingUp(User user) throws EmptyResultDataAccessException {
        return jdbcTemplate.queryForObject("SELECT id, firstname, lastname, email, password, role_id FROM users where users.email = ?  ",
                new BeanPropertyRowMapper<>(User.class), user.getEmail());
    }
}
