package com.example.demo.repository;

import com.example.demo.model.Address;
import com.example.demo.model.Email;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends CrudRepository<Address, Long> {
    List<Address> findAll();
}



