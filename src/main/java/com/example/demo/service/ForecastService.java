package com.example.demo.service;


import com.example.demo.model.Forecast;

import java.util.List;

public interface ForecastService {

    List<Forecast> showWeather(String city, int date) throws Exception;

    List<Forecast> addToDB(String city, int date) throws Exception;
}
