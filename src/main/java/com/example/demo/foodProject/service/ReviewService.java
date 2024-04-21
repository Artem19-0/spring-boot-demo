package com.example.demo.foodProject.service;

import com.example.demo.foodProject.model.Review;

import java.util.List;

public interface ReviewService {

    List<Review> getAll();
    Review create(Review review);
    void update(Review review);
    void delete(int id);


}
