package com.example.demo.foodProject.repository;


import com.example.demo.foodProject.model.Product;
import com.example.demo.foodProject.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe,Integer> {


    List<Recipe> findRecipesByCategory(String category);
    Recipe findRecipeByName(String name);
    List<Recipe> findRecipesByNameStartsWith(String name);
    List<Recipe> findRecipesByProductsIn(List<Product> products);


}
