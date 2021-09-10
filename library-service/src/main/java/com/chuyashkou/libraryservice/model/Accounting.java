package com.chuyashkou.libraryservice.model;

import lombok.Data;

@Data
public class Accounting {

    Long id;

    String date;

    String status;

    Long bookId;

    Long clientId;

    Long userId;

    Book book;

    Client client;

    User user;

}
