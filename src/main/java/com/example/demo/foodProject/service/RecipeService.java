package com.example.demo.foodProject.service;

import com.example.demo.foodProject.model.Product;
import com.example.demo.foodProject.model.Recipe;

import java.util.List;

public interface RecipeService {
    List<Recipe> getAllRecipes();
    Recipe getById(int id);// на пример для просмотра всей инфы рецепта
    List<Recipe> getByName(String name);
    List<Recipe> getRecipeByProducts(List<Product> products);
    List<Recipe> findByCategory(String category);

    void createRecipe(Recipe recipe);
    void update(Recipe recipe);
    void delete(int id);

}
