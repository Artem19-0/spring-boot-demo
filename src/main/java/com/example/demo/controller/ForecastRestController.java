package com.example.demo.controller;


import com.example.demo.model.Forecast;
import com.example.demo.service.ForecastServiceImpl;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest_weather")
public class ForecastRestController {

    @Autowired
    private static final Logger logger = LoggerFactory.getLogger(ForecastRestController.class);
    ForecastServiceImpl service;

    @Autowired
    public void setService(ForecastServiceImpl service) {
        this.service = service;
    }



    @GetMapping("/show_rest_weather/{city}/{to}")
    public List<Forecast> showWeather(@PathVariable(name = "city") String city ,
                                      @PathVariable(name = "to") int to) throws Exception {
        logger.info("show the weather", HttpStatus.FOUND);
        return service.showWeather(city, to);
    }



    @PostMapping("/add_to_db/{city}/{to}")
    public ResponseEntity<String> AddToDB(@PathVariable(name = "city") String city,
                                          @PathVariable(name = "to") int to) throws Exception {

        List<Forecast> forecasts = service.addToDB(city, to);
        if (forecasts.isEmpty()){
            logger.info("Ooops, somethind went wrong :(", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>("Ooops, somethind went wrong ;)", HttpStatus.INTERNAL_SERVER_ERROR);
        }else {
            logger.info("add weather to spring_db", HttpStatus.FOUND);
            return new ResponseEntity<>("add weather to spring_db successfully ;)", HttpStatus.OK);
        }
    }




}
