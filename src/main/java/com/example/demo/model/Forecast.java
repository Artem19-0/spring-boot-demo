package com.example.demo.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "forecast")
@Data
@AllArgsConstructor@NoArgsConstructor
public class Forecast {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private double precip;

    @Column(name = "max_temp")
    private double maxTemp;

    @Column(name = "min_temp")
    private double minTemp;

    @Column
    private Date date;

    @Column
    private String source;

    public Forecast(double precip, double maxTemp, double minTemp, Date date, String source) {
        this.precip = precip;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.date = date;
        this.source = source;
    }
}
