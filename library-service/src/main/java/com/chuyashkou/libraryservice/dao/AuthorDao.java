package com.chuyashkou.libraryservice.dao;


import com.chuyashkou.libraryservice.mapper.AuthorRowMapper;
import com.chuyashkou.libraryservice.model.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class AuthorDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public boolean addAuthor(Author author) throws EmptyResultDataAccessException {
        return jdbcTemplate.update("INSERT INTO AUTHORS (authors.firstname, authors.lastname) " +
                "VALUES (?,?)", author.getFirstname(), author.getLastname()) > 0;
    }

    public Author getAuthor(Author author) throws EmptyResultDataAccessException {
        return jdbcTemplate.queryForObject("SELECT id FROM authors where authors.firstname = ? and authors.lastname = ?  ",
                new BeanPropertyRowMapper<>(Author.class), author.getFirstname(), author.getLastname());
    }

    public Author getAuthorById(Long id) {
        return jdbcTemplate.queryForObject("SELECT firstname, lastname FROM authors where authors.id = ?  ",
                new BeanPropertyRowMapper<>(Author.class), id);
    }

    public List<Author> getAllAuthors() {
        return jdbcTemplate.query("SELECT * FROM authors", new AuthorRowMapper());
    }


    public boolean deleteAuthorById(Long id) {
        return jdbcTemplate.update("DELETE FROM authors where authors.id = ? ", id) > 0;
    }

    public boolean updateAuthorById(Author author) {
        return jdbcTemplate.update("UPDATE authors SET authors.firstname = ?, authors.lastname = ? where authors.id = ? ", author.getFirstname(), author.getLastname(), author.getId()) > 0;
    }
}
