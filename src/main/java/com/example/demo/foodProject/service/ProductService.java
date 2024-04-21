package com.example.demo.foodProject.service;

import com.example.demo.foodProject.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAll();
    Product getById(int id);
    Product findByName(String name);
    void createProduct(Product product);
    void update(Product product);

    void delete(int id);
}
