package com.example.demo.repository;

import com.example.demo.model.Email;
import com.example.demo.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailsRepository extends CrudRepository<Email, Long> {
    List<Email> findAll();
}
