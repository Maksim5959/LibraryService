package com.chuyashkou.libraryservice.mapper;

import com.chuyashkou.libraryservice.model.Author;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class AuthorRowMapper implements RowMapper<Author> {

    @Override
    public Author mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Author author = new Author();
        author.setId(resultSet.getLong(1));
        author.setLastname(resultSet.getString(2));
        author.setFirstname(resultSet.getString(3));
        return author;
    }
}
