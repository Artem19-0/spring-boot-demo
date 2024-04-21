package com.example.demo.service;

import com.example.demo.dto.User1Dto;
import com.example.demo.model.User1;

import java.util.List;

public interface User1Service {

    void saveUser(User1Dto userDto);
    User1 findUserByEmail(String email);
    List<User1Dto> findAllUsers();

}
