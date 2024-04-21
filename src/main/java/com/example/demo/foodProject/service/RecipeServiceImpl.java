package com.example.demo.foodProject.service;

import com.example.demo.foodProject.model.Product;
import com.example.demo.foodProject.model.Recipe;
import com.example.demo.foodProject.repository.RecipeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;


import java.util.List;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService{


    private RecipeRepository repository;

    @Autowired
    public RecipeServiceImpl(RecipeRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Recipe> getAllRecipes() {
        List<Recipe> recipesList = repository.findAll();
        if (recipesList.isEmpty()){
            return null;
        }else {
            return recipesList;
        }
    }



    @Override
    public Recipe getById(int id) {
        try {
            return repository.getReferenceById(id);
        }catch (NullPointerException e){
            e.printStackTrace();
            log.error("Sorry, I couldn't find :(");
        }
        return null;

    }

    @Override
    public List<Recipe> getByName(String name) {
        try {
            return repository.findRecipesByNameStartsWith(name);
        }catch (NullPointerException e){
            e.printStackTrace();
            log.error("Sorry, I couldn't find :(");
        }
        return null;
    }


    @Override
    public List<Recipe> getRecipeByProducts(List<Product> products) {
        try {
            return repository.findRecipesByProductsIn(products);
        }catch (EmptyResultDataAccessException e){
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public List<Recipe> findByCategory(String category) {
        try {
            return repository.findRecipesByCategory(category);
        }catch (EntityNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void createRecipe(Recipe recipe) {
        try {
            recipe.setProducts(recipe.getProducts());
        repository.save(recipe);
        }catch (NullPointerException | IllegalArgumentException e){
            e.printStackTrace();
        }
    }


    @Override
    public void update(Recipe recipe) {
       try {
           Recipe recipeToUpdate = repository.getReferenceById(recipe.getId());
           recipeToUpdate = recipe;
           repository.save(recipeToUpdate);
       }catch (NullPointerException | IllegalArgumentException e){
           e.printStackTrace();
       }
    }

    @Override
    public void delete(int id) {
        try {
            repository.deleteById(id);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
    }


}
