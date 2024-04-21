package com.example.demo.foodProject.controller;


import com.example.demo.foodProject.model.Recipe;
import com.example.demo.foodProject.service.ProductService;
import com.example.demo.foodProject.service.ProductServiceImpl;
import com.example.demo.foodProject.service.RecipeService;
import com.example.demo.foodProject.service.UserFoodService;
import jakarta.persistence.Access;
import lombok.extern.slf4j.Slf4j;
import org.fusesource.jansi.Ansi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/recipe")
public class RecipeController {



    private RecipeService serviceRecipe;

    private ProductService serviceProduct;
    private UserFoodService serviceUser;
    private Ansi ansi;


    @Autowired
    public RecipeController(RecipeService serviceRecipe , ProductService serviceProduct,UserFoodService serviceUser) {
        this.serviceRecipe = serviceRecipe;
        this.serviceProduct = serviceProduct;
        this.ansi = Ansi.ansi();
        this.serviceUser = serviceUser;
    }



    @GetMapping("all")
    public String showRecipes(Model model){
        List<Recipe> recipes = serviceRecipe.getAllRecipes();
        model.addAttribute("recipes", recipes);
        model.addAttribute("recipe" , new Recipe());
        System.out.println(ansi.fg(Ansi.Color.GREEN).toString() + "ПОКАЗЫВАЮ ВСЕ РЕЦЕПТЫ");
        return "all_recipes";
    }


    @GetMapping("/create_form")
    public String showCreateForm(Model model){
        model.addAttribute("recipe", new Recipe());
        model.addAttribute("products", serviceProduct.getAll());
        System.out.println(ansi.fg(Ansi.Color.GREEN).toString() + "CREATE RECIPE METHOD");
        return "create_recipe";
    }

    @PostMapping("/create")
    public String create(@Validated Recipe recipe){
        recipe.setUser(serviceUser.findById(2));//временно
        serviceRecipe.createRecipe(recipe);
        return "redirect:/recipe/all";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") int id, Model model){
        System.out.println(ansi.fg(Ansi.Color.BLUE).toString() + "EDIT RECIPE METHOD");
        model.addAttribute("recipe", serviceRecipe.getById(id));
        model.addAttribute("products", serviceProduct.getAll());
        return "edit_form_recipe";
    }

    @Transactional
    @PostMapping("/edit")
    public String edit(@Validated Recipe recipe){
        recipe.setImage(serviceRecipe.getById(recipe.getId()).getImage());
        recipe.setUser(serviceUser.findById(2));
        serviceRecipe.update(recipe);
        System.out.println(ansi.fg(Ansi.Color.BLUE).toString() + "EDIT WAS SUCCESSFUL");
        return "redirect:/recipe/all";
    }



    @GetMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") int id){
        System.out.println(ansi.fg(Ansi.Color.RED).toString() + "DELETE RECIPE METHOD");
        serviceRecipe.delete(id);
        return "redirect:/recipe/all";
    }


    @GetMapping("/search_name")
    public String searchByName(@RequestParam String name,Model model ){
        model.addAttribute("recipes", serviceRecipe.getByName(name));

        return "all_recipes";
    }




// todo дальше всё связанно с продуктами

    @GetMapping("/product/all")
    public String showAllProducts(Model model){
        log.info("SHOW PRODUCT BY ID");
        model.addAttribute("products" , serviceProduct.getAll());
        return "products";
    }


    @GetMapping("/product/{id}")
    public String showProduct(@PathVariable(name = "id") int id, Model model){
        log.info("SHOW PRODUCT BY ID");
        model.addAttribute("product" , serviceProduct.getById(id));
        return "product_info";
    }




}
