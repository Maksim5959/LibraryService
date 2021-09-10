package com.chuyashkou.libraryservice.model;

import lombok.Data;

@Data
public class Book {

    private Long id;

    private Long authorId;

    private Long publishingHouseId;

    private Long clientId;

    private Long userId;

    private String year;

    private String title;

    private Author author;

    private PublishingHouse publishingHouse;

    private Client client;

    private User user;

}
