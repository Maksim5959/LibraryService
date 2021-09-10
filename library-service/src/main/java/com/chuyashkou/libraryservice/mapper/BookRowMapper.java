package com.chuyashkou.libraryservice.mapper;

import com.chuyashkou.libraryservice.model.Book;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class BookRowMapper implements RowMapper<Book> {

    @Override
    public Book mapRow(ResultSet resultSet, int i) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getLong(1));
        book.setTitle(resultSet.getString(2));
        book.setYear(resultSet.getString(3));
        book.setUserId(resultSet.getLong(6));
        book.setAuthorId(resultSet.getLong(5));
        book.setClientId(resultSet.getLong(4));
        book.setPublishingHouseId(resultSet.getLong(7));
        return book;
    }
}
