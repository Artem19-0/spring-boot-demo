package com.example.demo.foodProject.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "recipes")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column(nullable = false)
    private String name;


    @Column(name = "cooking_method" , nullable = false, length = 3000)
    private String cookingMethod;


    @ManyToOne
    @JoinColumn(name = "user_nickname", nullable = false)
    private UserFood user;


    @Column(name = "cooking_time",nullable = false)
    private int cookingTime;


    @Column(nullable = false)
    private String category;

    @Column
    private String image;


    @ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @JoinTable(name = "recipes_products", joinColumns = { @JoinColumn(name = "recipe_name") },
            inverseJoinColumns = {@JoinColumn(name = "product_name") })
    private List<Product> products;



    @OneToMany(mappedBy = "recipe")
    private List<Review> reviews;


    @Column(name = "created_ts")
    private Timestamp createdTs;


    public String getImage() {
        return image;
    }



}
