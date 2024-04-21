package com.example.demo.foodProject.service;

import com.example.demo.foodProject.model.Review;
import com.example.demo.foodProject.repository.ProductRepository;
import com.example.demo.foodProject.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService{


    @Autowired
    private ReviewRepository repository;



    @Override
    public List<Review> getAll() {
        List<Review> reviewList = repository.findAll();
        if (reviewList.isEmpty()){
            throw new NullPointerException();
        }else {
            return reviewList;
        }
    }


    @Override
    public Review create(Review review) {
        return repository.save(review);
    }

    @Override
    public void update(Review review) {
        Review reviewUpdate = repository.getById(review.getId());
        if (reviewUpdate == null){
            throw new NullPointerException();
        }
        reviewUpdate = review;
        repository.save(reviewUpdate);
    }

    @Override
    public void delete(int id) {
        repository.delete(repository.getById(id));
    }
}
