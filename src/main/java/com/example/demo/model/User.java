package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;


    @Column(unique=true)
    private Long personNumber;


    @OneToMany(mappedBy = "user")
    private List<Address> addresses;


    @OneToMany(mappedBy = "user")
    private List<Email> emails;
}
