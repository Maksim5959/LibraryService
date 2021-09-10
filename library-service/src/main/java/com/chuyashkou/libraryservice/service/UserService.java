package com.chuyashkou.libraryservice.service;

import com.chuyashkou.libraryservice.dao.UserDao;
import com.chuyashkou.libraryservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserDao userDao = new UserDao();

    public boolean addUser(User user) {
        return userDao.addUser(user);
    }

    public User getUser(User user) {
        try {
            return userDao.getUser(user);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public User getUserById(Long id) {
        return userDao.getUserById(id);
    }

}
