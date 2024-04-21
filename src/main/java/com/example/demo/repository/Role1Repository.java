package com.example.demo.repository;

import com.example.demo.model.Role1;
import org.springframework.data.jpa.repository.JpaRepository;


public interface Role1Repository extends JpaRepository<Role1, Long> {
    Role1 findByName(String name);
}
