package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column()
    private String name;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String password;

    @Column()
    private String gender;

    @Column()
    private String note;

    @Column()
    private boolean married;

    @Column()
    private Date birthday;

    @Column()
    private String profession;
}
