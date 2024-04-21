package com.example.demo.foodProject.controller;


import com.example.demo.foodProject.model.Role;
import com.example.demo.foodProject.model.UserFood;
import com.example.demo.foodProject.service.ProductService;
import com.example.demo.foodProject.service.RecipeService;
import com.example.demo.foodProject.service.UserFoodService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.fusesource.jansi.Ansi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@Controller
@RequestMapping("/food")
public class UserFoodController {


    private UserFoodService userService;

    private Ansi ansi;


    @Autowired
    public UserFoodController(UserFoodService userService, ProductService serviceProduct) {
        this.userService = userService;
        this.ansi = Ansi.ansi();
    }


    @GetMapping("/home")
    public String home(){
        System.out.println(ansi.fg(Ansi.Color.GREEN).toString() + "HOME PAGE");
        return "home_page";
    }


    @GetMapping("/all")
    public String showAllUsers(Model model){
        List<UserFood> users = userService.getAll();
        model.addAttribute("users", users);
        System.out.println("VIEW USERS");
        return "all_users";
    }



    @GetMapping("/register")
    public String registerForm(Model model){
        model.addAttribute("user", new UserFood());
        return "register_user";
    }


    @Transactional
    @PostMapping("/register/save")
    public String register(@Validated @ModelAttribute("user")UserFood userFood , BindingResult result, Model model){

        System.out.println(ansi.fg(Ansi.Color.GREEN).toString() + "REGISTER METHOD");

        UserFood existingUser1 = userService.findByNickname(userFood.getNickname());
        // (1)The first "if": if user doesn't write nickname
        if (userFood.getNickname().isEmpty()) {
            result.rejectValue("nickname", null,
                    "You have to write nickname");

        }// (2)The second "if": if user exists with the same nickname
        else if (existingUser1 != null && existingUser1.getNickname() != null && !existingUser1.getNickname().isEmpty()){
            result.rejectValue("nickname", null,
                    "There is already an account registered with the same nickname");
        }




        UserFood existingUser2 = userService.findByEmail(userFood.getEmail());
        // (1)The first "if": if user doesn't write email
        if (userFood.getEmail().isEmpty()) {
            result.rejectValue("email", null,
                    "You have to write email");


        } // (2)The second "if": if user exists with the same email
        else if (existingUser2 != null && existingUser2.getEmail() != null && !existingUser2.getEmail().isEmpty()){
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }





        UserFood existingUser3 = userService.findByPassword(userFood.getPassword());
        // (1)The first "if": if user doesn't write password
        if (userFood.getPassword().isEmpty()) {
            result.rejectValue("password", null,
                    "You have to write password");


        } // (2)The second "if": if user exists with the same password
        else if (existingUser3 != null && existingUser3.getPassword() != null
                && !existingUser3.getPassword().isEmpty()){

            result.rejectValue("password", null,
                    "There is already an account registered with the same password");

        }


        // show user error about errors
        if(result.hasErrors()){
            model.addAttribute("user", userFood);
            return "/register_user";
        }

       //save user if everything is correct
        userFood.setCountry("Belarus");
        userService.create(userFood);
        System.out.println(ansi.fg(Ansi.Color.BLUE).toString() + "REGISTRATION WAS SUCCESSFUL");

        return "redirect:/food/all";
    }




    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("user" , new UserFood());
        System.out.println(ansi.fg(Ansi.Color.GREEN).toString() + "LOGIN METHOD");
        return "login_user";
    }


    @PostMapping("/login_user")
    public String login(@Validated UserFood userFood, Model model){
            UserFood userToCheck = userService.findByNickname(userFood.getNickname());
            if (userToCheck != null && userToCheck.getPassword().equals(userFood.getPassword())){
                System.out.println(ansi.fg(Ansi.Color.WHITE).toString() + "LOGIN WAS SUCCESSFUL");
                return "redirect:/food/home";
            }else {
                model.addAttribute("error", userFood);
                return "/login_user";
            }

    }





    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") int id,Model model){
        UserFood userFood = userService.findById(id);
        model.addAttribute("user" , userFood);
        model.addAttribute("id", id);
        System.out.println(ansi.fg(Ansi.Color.RED).toString() + "EDIT METHOD");
        return "edit_form_user";
    }

    @Transactional
    @PostMapping("/edit")
    public String edit(@Valid UserFood userFood){
        userService.update(userFood);
        return "redirect:/food/all";
    }





    @GetMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") int id){
        userService.delete(id);
        return "redirect:/food/all";
    }










}
