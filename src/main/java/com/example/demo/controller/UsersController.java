package com.example.demo.controller;


import com.example.demo.model.Address;
import com.example.demo.model.User;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.EmailsRepository;
import com.example.demo.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UsersController {


    private UsersRepository userRepository;
    private AddressRepository addressRepository;
    private EmailsRepository emailsRepository;


    @Autowired
    public void setUserRepository(UsersRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setEmailsRepository(EmailsRepository emailsRepository) {
        this.emailsRepository = emailsRepository;
    }

    @Autowired
    public void setAddressRepository(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }





    @GetMapping
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }



    @GetMapping("/{id}")
    public ResponseEntity<User> findUserById(@PathVariable(value = "id") long id) {
        Optional<User> user = userRepository.findById(id);

        if(user.isPresent()) {
            return ResponseEntity.ok().body(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping
    public User saveUser(@Validated @RequestBody User user) {
       User result = null;
        try {
            result = userRepository.save(user);
            long id = result.getId();
            user.getAddresses().forEach( address -> {
                User u = new User();
                u.setId(id);
                address.setUser( u);
                Address add = addressRepository.save(address);
                System.out.println(add);
            });

            user.getEmails().forEach( email -> {
                User u = new User();
                u.setId(id);
                email.setUser(u);
                emailsRepository.save(email);
            });

        } catch (Exception e){
            return null;
        }

        return result;
    }





}
