package com.example.demo.foodProject.repository;

import com.example.demo.foodProject.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {

    @Query("select p from Product p join p.recipes r where r.id=:id")
    List<Product> findProductsByRecipe(@Param("id") int id);
    Product findProductByName(String name);


}
