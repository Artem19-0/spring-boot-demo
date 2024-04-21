package com.example.demo.service;

import com.example.demo.dto.CurrencyDto;
import com.example.demo.model.Currency;
import com.example.demo.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CurrencyServiceImpl implements CurrencyService{

    @Autowired
    CurrencyRepository repo;



    @Override
    public List<CurrencyDto> getAll() {
        List<CurrencyDto> dtos = new ArrayList<>();
        List<Currency> currencyList = repo.findAll();
        currencyList.forEach(currency -> {
            dtos.add(new CurrencyDto(currency));
        });
        return dtos;
    }

    @Override
    public List<CurrencyDto> getForceAll() {
        return null;
    }


    @Override
    public void saveAllCurrencies() {
        repo.saveAll(CurrencyParser.getAllCurrencies());

    }

    @Override
    public CurrencyDto getByCharCode(String charCode) {
        List<Currency> currencies = repo.findAll();
        for (Currency currency1: currencies) {
            if (currency1 != null) {
                if (currency1.getCharCode().equals(charCode)) {
                    return new CurrencyDto(currency1);
                }
            }else {
                return null;
            }
        }
        return null;
    }


    @Transactional
    @Override
    public void deleteById(int id) {
        repo.deleteById(id);
    }




    // call service
//    private List<Currency> load(){
//    return null;
//    }
}
