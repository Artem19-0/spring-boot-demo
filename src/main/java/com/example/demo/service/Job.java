package com.example.demo.service;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Service
public class Job {

    @Autowired
    private CurrencyService service;

    @GetMapping("/test")
  //  @Scheduled(fixedDelay = 2000)
    public void test(){
        System.out.println("Refresh Currencies job has been started");
        service.saveAllCurrencies();
        System.out.println("Refresh Currencies job has been finished");
    }
}
