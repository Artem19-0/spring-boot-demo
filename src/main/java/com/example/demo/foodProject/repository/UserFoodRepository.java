package com.example.demo.foodProject.repository;

import com.example.demo.foodProject.model.UserFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFoodRepository extends JpaRepository<UserFood, Integer> {

    UserFood findUserFoodByNickname(String nickname);
    UserFood findUserFoodByEmail(String email);
    UserFood findUserFoodByPassword(String password);
}
