package com.example.demo.repository;

import com.example.demo.model.User1;
import org.springframework.data.jpa.repository.JpaRepository;


public interface User1Repository extends JpaRepository<User1, Long> {
    User1 findByEmail(String email);

}
