package com.example.demo.service;

import com.example.demo.dto.CurrencyDto;
import com.example.demo.model.Currency;

import java.util.List;

public interface CurrencyService {

    List<CurrencyDto> getAll();
    List<CurrencyDto> getForceAll();
    CurrencyDto getByCharCode(String charCode);
    void saveAllCurrencies();

    void deleteById(int id);


}
