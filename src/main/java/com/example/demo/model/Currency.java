package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "currencies")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Currency {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "num_code")
    @JsonProperty(value = "NumCode")
    private Integer numCode;

    @Column(name = "char_code", nullable = false, unique = true)
    @JsonProperty(value = "CharCode")
    private String charCode;

    @Column
    @JsonProperty(value = "Scale")
    private Integer scale;

    @Column
    @JsonProperty(value = "Name")
    private String name;

    @Column
    @JsonProperty(value = "Rate")
    private Double rate;

    @Column(name = "daily_ts")
    private Timestamp dailyTs;

    @Column(name = "updated_ts")
    private Timestamp updatedTs;





}
