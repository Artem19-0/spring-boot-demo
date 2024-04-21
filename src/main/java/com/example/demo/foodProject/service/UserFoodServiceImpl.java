package com.example.demo.foodProject.service;

import com.example.demo.foodProject.model.UserFood;
import com.example.demo.foodProject.repository.UserFoodRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserFoodServiceImpl implements UserFoodService{


    @Autowired
    private UserFoodRepository repository;



    @Override
    public List<UserFood> getAll() {
        List<UserFood> userFoodList = repository.findAll();
        if (userFoodList.isEmpty()){
            return null;
        }else {
            return userFoodList;
        }
    }


    @Override
    public UserFood findById(int id) {
        try {
            UserFood userFood = repository.getById(id);
            if (userFood == null) {
                return null;
            } else {
                return userFood;
            }
        }catch (EntityNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public UserFood findByEmail(String email) {
         UserFood user = repository.findUserFoodByEmail(email);
         if (user == null){
             return null;
         }else {
             return user;
         }
    }

    @Override
    public UserFood findByNickname(String nickname) {
        UserFood user = repository.findUserFoodByNickname(nickname);
        if (user == null){
            return null;
        }else {
            return user;
        }
    }

    @Override
    public UserFood findByPassword(String password) {
        UserFood user = repository.findUserFoodByPassword(password);
        if (user == null){
            return null;
        }else {
            return user;
        }
    }






    @Override
    public void create(UserFood userFood) {
        if (userFood != null){
                if (userFood.getNickname() == null && userFood.getEmail() == null
                        && userFood.getCountry() == null ){
                    throw new IllegalArgumentException();
                }
            repository.save(userFood);
        }else {
            throw new NullPointerException();
        }
    }



    @Override
    public UserFood update(UserFood userFood) {
        UserFood etity = findById(userFood.getId());
        if (etity == null){
            return null;
        }else {
            if (!userFood.getPassword().equals(etity.getPassword())){
                etity.setPassword(userFood.getPassword());
            }
            etity.setNickname(userFood.getNickname());
            etity.setEmail(userFood.getEmail());
            etity.setCountry(userFood.getCountry());
            return repository.save(etity);
        }

    }




    @Override
    public void delete(int id) {
        try {
            UserFood userFood= repository.getById(id);
            repository.delete(userFood);
        }catch (EntityNotFoundException e){
            e.printStackTrace();
        }
    }




}
