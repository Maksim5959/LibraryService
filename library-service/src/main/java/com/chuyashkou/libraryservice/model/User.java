package com.chuyashkou.libraryservice.model;

import lombok.Data;

@Data
public class User {

    private Long id;

    private String email;

    private String password;

    private String firstname;

    private String lastname;

    private Long roleId;

}
