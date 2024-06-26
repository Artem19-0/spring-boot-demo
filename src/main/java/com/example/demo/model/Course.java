package com.example.demo.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "COURSE")
public class Course {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_sequence")
    @SequenceGenerator(name = "course_sequence", sequenceName = "course_sequence")
    private Integer id;


    @Column(name = "name")
    private String name;


    @ManyToMany(mappedBy = "courses")
    @JsonIgnore
    private Set<Student> students;
}
