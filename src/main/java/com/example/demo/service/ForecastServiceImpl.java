package com.example.demo.service;

import com.example.demo.model.Forecast;
import com.example.demo.repository.ForecastRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForecastServiceImpl implements ForecastService{


    @Autowired
    ForecastRepository repository;

    @Autowired
    WeatherAPIService weatherService;


    @Override
    public List<Forecast> showWeather(String city, int date) throws Exception {
        return weatherService.timelineRequestHttpClient(city,date);
    }

    @Override
    public List<Forecast> addToDB(String city, int date) throws Exception {
        List<Forecast> forecasts = weatherService.timelineRequestHttpClient(city,date);
        return repository.saveAll(forecasts);
    }



}
