package com.example.demo.controller;


import com.example.demo.dto.CurrencyDto;
import com.example.demo.model.Currency;
import com.example.demo.repository.CurrencyRepository;
import com.example.demo.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/currencies")
public class CurrencyRestController {



    private CurrencyService service;

    @Autowired
    public void setRepo(CurrencyService service) {
        this.service = service;
    }

    @GetMapping("/save_all")
    public ResponseEntity<String> save(){
        service.saveAllCurrencies();
        return new ResponseEntity<>("currencies saved successfully", HttpStatus.OK);
    }

    @GetMapping("/get_all")
    public ResponseEntity<List<CurrencyDto>> getAllCurrencies(){
        List<CurrencyDto> dtoList = service.getAll();
        return  new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

//    @GetMapping("/test")
//    @Scheduled(fixedRate = 3000)
//    public void test(){
//        System.out.println("TEST");
//    }



    @GetMapping("/get_by_code/{code}")
    public ResponseEntity<CurrencyDto> getById(@PathVariable(name = "code") String  code){
        CurrencyDto dto = service.getByCharCode(code);
        return  new ResponseEntity<>(dto, HttpStatus.OK);
    }


    @GetMapping("/get_by_char_code/{charCode}")
    public ResponseEntity<CurrencyDto> getByCharCode(@PathVariable(value = "charCode") String charCode){
        CurrencyDto dto = service.getByCharCode(charCode);
        return new ResponseEntity<>(dto, HttpStatus.FOUND);
    }


    @PostMapping("/delete_by_id/{id}")
    public ResponseEntity<String> deleteById(@PathVariable(value = "id") int id){
        service.deleteById(id);
        return new ResponseEntity<>("currencies deleted successfully", HttpStatus.OK);
    }





}
