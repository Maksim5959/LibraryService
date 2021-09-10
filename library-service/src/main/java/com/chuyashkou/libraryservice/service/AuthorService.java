package com.chuyashkou.libraryservice.service;


import com.chuyashkou.libraryservice.dao.AuthorDao;
import com.chuyashkou.libraryservice.model.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    @Autowired
    AuthorDao authorDao = new AuthorDao();

    public boolean addAuthor(Author author) {
        try {
            authorDao.getAuthor(author);
            return false;
        } catch (EmptyResultDataAccessException e) {
            authorDao.addAuthor(author);
            return true;
        }
    }

    public Author getAuthor(Author author) {
        try {
            return authorDao.getAuthor(author);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Author getAuthorById(Long id) {
        return authorDao.getAuthorById(id);
    }

    public List<Author> getAllAuthors() {
        return authorDao.getAllAuthors();
    }

    public boolean deleteAuthorById(Long id) {
        return authorDao.deleteAuthorById(id);
    }

    public boolean updateAuthorById(Author author) {
        return authorDao.updateAuthorById(author);
    }
}
