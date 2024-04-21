package com.example.demo.controller;

import com.example.demo.dto.User1Dto;
import com.example.demo.model.User1;
import com.example.demo.service.User1Service;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AuthController {

    private User1Service user1Service;

    public AuthController(User1Service user1Service) {
        this.user1Service = user1Service;
    }


    // handler method to handle home page request
    @GetMapping("/index")
    public String home(){
        return "index";
    }



    // handler method to handle user registration form request
    @GetMapping("/register1")
    public String showRegistrationForm(Model model){
        // create model object to store form data
        User1Dto user1 = new User1Dto();
        model.addAttribute("user", user1);
        return "register1";
    }



    // handler method to handle user registration form submit request
    @PostMapping("/register1/save")
    public String registration(@Valid @ModelAttribute("user") User1Dto user1Dto, BindingResult result, Model model){

        User1 existingUser = user1Service.findUserByEmail(user1Dto.getEmail());
        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if(result.hasErrors()){
            model.addAttribute("user", user1Dto);
            return "/register1";
        }

        user1Service.saveUser(user1Dto);
        return "redirect:/register1?success";

    }




    // handler method to handle list of users
    @GetMapping("/users")
    public String users(Model model){
        List<User1Dto> users = user1Service.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }



    // handler method to handle login request
    @GetMapping("/login1")
    public String login(){
        return "login1";
    }



}
