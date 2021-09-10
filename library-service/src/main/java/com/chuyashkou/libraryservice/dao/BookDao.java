package com.chuyashkou.libraryservice.dao;

import com.chuyashkou.libraryservice.mapper.BookRowMapper;
import com.chuyashkou.libraryservice.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public boolean addBook(Book book) throws EmptyResultDataAccessException {
        return jdbcTemplate.update("INSERT INTO BOOKS (books.title,books.year, books.id_publishing_house, books.id_client, " +
                        "books.id_author, books.id_user) VALUES (?,?, (SELECT id FROM publishing_houses where publishing_houses.title = ? and publishing_houses.address = ? ), " +
                        "(SELECT id FROM clients where clients.lastname = ? and clients.birthday = ?)," +
                        "(SELECT id FROM authors where authors.firstname = ? and authors.lastname = ?)," +
                        "(SELECT id FROM users where users.email = ?))"
                , book.getTitle(), book.getYear(), book.getPublishingHouse().getTitle(),book.getPublishingHouse().getAddress(), book.getClient().getLastname(),
                book.getClient().getBirthday(), book.getAuthor().getFirstname(), book.getAuthor().getLastname(),
                book.getUser().getEmail()) > 0;
    }

    public Book getBook(Book book) throws EmptyResultDataAccessException {
        return jdbcTemplate.queryForObject("SELECT id FROM books where books.title = ? ",
                new BeanPropertyRowMapper<>(Book.class), book.getTitle());
    }


    public List<Book> getAllBooks() {
        return jdbcTemplate.query("SELECT * FROM books", new BookRowMapper());
    }

    public Book getBookById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM books where books.id = ?  ", new BookRowMapper(), id);
    }

    public boolean deleteBookById(Long id) {
        return jdbcTemplate.update("DELETE FROM books where books.id = ? ", id) > 0;
    }

    public boolean updateBookByAuthor(Book book, Long authorId) {
        return jdbcTemplate.update("UPDATE books SET books.id_author = ? where books.id = ? ", authorId, book.getId()) > 0;
    }

    public boolean updateBookByPublishingHouse(Book book, Long publishingHouseId) {
        return jdbcTemplate.update("UPDATE books SET books.id_publishing_house = ? where books.id = ? ", publishingHouseId, book.getId()) > 0;
    }

    public boolean updateBookByClient(Book book, Long clientId) {
        return jdbcTemplate.update("UPDATE books SET books.id_client = ? where books.id = ? ", clientId, book.getId()) > 0;
    }

    public boolean updateBookByUser(Book book, Long userId) {
        return jdbcTemplate.update("UPDATE books SET books.id_user = ? where books.id = ? ", userId, book.getId()) > 0;
    }

    public boolean updateTitleById(Book book) {
        return jdbcTemplate.update("UPDATE books SET books.title = ?, books.year = ? where books.id = ? ", book.getTitle(), book.getYear(), book.getId()) > 0;
    }

    public List<Book> getBooksByClientId(Long clientId) {
        return jdbcTemplate.query("SELECT * FROM books where books.id_client = ? ",
                new BookRowMapper(), clientId);
    }

    public List<Book> getBooksByAuthorId(Long authorId) {
        return jdbcTemplate.query("SELECT * FROM books where books.id_author = ? ",
                new BookRowMapper(), authorId);
    }

    public List<Book> getBooksByPublishingHouseId(Long publishingHouseId) {
        return jdbcTemplate.query("SELECT * FROM books where books.id_publishing_house = ? ",
                new BookRowMapper(), publishingHouseId);
    }
}
