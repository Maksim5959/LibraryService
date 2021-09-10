package com.chuyashkou.libraryservice.service;


import com.chuyashkou.libraryservice.dao.BookDao;
import com.chuyashkou.libraryservice.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private final BookDao bookDao = new BookDao();


    public boolean addBook(Book book) {
        try {
            bookDao.getBook(book);
            return false;
        } catch (EmptyResultDataAccessException e) {
            bookDao.addBook(book);
            return true;
        }

    }

    public Book getBook(Book book) {
        try {
            return bookDao.getBook(book);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Book> getAllBook() {
        return bookDao.getAllBooks();
    }

    public boolean deleteBookById(Long id) {
        return bookDao.deleteBookById(id);
    }

    public Book getBookById(Long id) {
        return bookDao.getBookById(id);
    }

    public boolean updateBookByAuthor(Book book, Long authorId) {
        return bookDao.updateBookByAuthor(book, authorId);
    }

    public boolean updateBookByPublishingHouse(Book book, Long publishingHouseId) {
        return bookDao.updateBookByPublishingHouse(book, publishingHouseId);
    }

    public boolean updateBookByClient(Book book, Long clientId) {
        return bookDao.updateBookByClient(book, clientId);
    }

    public boolean updateBookByUser(Book book, Long userId) {
        return bookDao.updateBookByUser(book, userId);
    }

    public boolean updateTitleById(Book book) {
        return bookDao.updateTitleById(book);
    }

    public List<Book> getBooksByClientId(Long clientId) {
        return bookDao.getBooksByClientId(clientId);
    }

    public List<Book> getBooksByAuthorId(Long authorId) {
        return bookDao.getBooksByAuthorId(authorId);
    }

    public List<Book> getBooksByPublishingHouseId(Long publishingHouseId) {
        return bookDao.getBooksByPublishingHouseId(publishingHouseId);
    }
}
