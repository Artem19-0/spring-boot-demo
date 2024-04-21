package com.example.demo.dto;

import com.example.demo.model.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyDto {

    private Integer numCode;
    private String charCode;
    private Integer scale;
    private String name;
    private Double rate;

    private Double scaleOneRate;

    private Timestamp dailyTs;
    private Timestamp updatedTs;

    public CurrencyDto(Currency currency) {
        this.name = currency.getName();
        this.numCode = currency.getNumCode();
        this.charCode = currency.getCharCode();
        this.scale = currency.getScale();
        this.rate = currency.getRate();
        this.scaleOneRate = currency.getScale() == 1 ? currency.getRate() : currency.getRate()/ currency.getScale();
        this.updatedTs = currency.getUpdatedTs();
        this.dailyTs = currency.getDailyTs();

    }
}
