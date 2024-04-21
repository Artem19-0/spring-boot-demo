package com.example.demo.foodProject.service;


import com.example.demo.foodProject.model.Product;
import com.example.demo.foodProject.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService{



    @Autowired
    private ProductRepository repository;


    @Override
    public List<Product> getAll(){
        try {
            return repository.findAll();
        }catch (EmptyResultDataAccessException e){
            e.printStackTrace();
            log.error("Sorry, I couldn't find :(");
        }
        return null;
    }

    @Override
    public Product getById(int id){
        try {
            log.info("I found product");
            return repository.getReferenceById(id);
        }catch (NullPointerException e){
            e.printStackTrace();
            log.error("Sorry, I couldn't find :(");
        }
        return null;
    }



    @Override
    public Product findByName(String name){
        Product product = repository.findProductByName(name);
       if ( product != null){
           log.info("I found product");
           return product;
       }else {
           log.error("Sorry, I couldn't find :(");
           return null;
       }
    }


    @Override
    public void createProduct(Product product){
        try {
            repository.save(product);
            log.info("I created product");
        }catch (IllegalArgumentException e){
            e.printStackTrace();
            log.error("Sorry, I couldn't create :(");
        }
    }

    @Override
    public void update(Product product){
        try {
            Product productUpdate = repository.getReferenceById(product.getId());
            productUpdate = product;
            repository.save(productUpdate);
            log.info("Product was updated");
        }catch (EntityNotFoundException e){
            e.printStackTrace();
            log.error("Sorry, I couldn't updated:(");
        }
    }

    @Override
    public void delete(int id){

    }
}
