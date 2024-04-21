package com.example.demo.service;

import com.example.demo.dto.User1Dto;
import com.example.demo.model.Role1;
import com.example.demo.model.User1;
import com.example.demo.repository.Role1Repository;
import com.example.demo.repository.User1Repository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class User1ServiceImpl implements User1Service {
    private User1Repository userRepository;
    private Role1Repository roleRepository;
    private PasswordEncoder passwordEncoder;


    public User1ServiceImpl(
                            Role1Repository roleRepository) { // +  PasswordEncoder passwordEncoder + User1Repository userRepository,
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void saveUser(User1Dto userDto) {
        User1 user = new User1();
        user.setName(userDto.getFirstName() + " " + userDto.getLastName());
        user.setEmail(userDto.getEmail());
        // encrypt the password using spring security
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Role1 role = roleRepository.findByName("ROLE_ADMIN");
        if(role == null){
            role = checkRoleExist();
        }
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }


    @Override
    public User1 findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    @Override
    public List<User1Dto> findAllUsers() {
        List<User1> users = userRepository.findAll();
        return users.stream()
                .map((user) -> mapToUserDto(user))
                .collect(Collectors.toList());
    }


    private User1Dto mapToUserDto(User1 user){
        User1Dto userDto = new User1Dto();
        String[] str = user.getName().split(" ");
        userDto.setFirstName(str[0]);
        userDto.setLastName(str[1]);
        userDto.setEmail(user.getEmail());
        return userDto;
    }


    private Role1 checkRoleExist(){
        Role1 role = new Role1();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }



}
