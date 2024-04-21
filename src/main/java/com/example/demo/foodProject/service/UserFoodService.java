package com.example.demo.foodProject.service;

import com.example.demo.foodProject.model.UserFood;

import java.util.List;

public interface UserFoodService {


    List<UserFood> getAll();
    UserFood findById(int id);
    UserFood findByEmail(String email);
    UserFood findByNickname(String nickname);
    UserFood findByPassword(String password);
    void create(UserFood userFood);
    UserFood update(UserFood userFood);
    void delete(int id);
}
