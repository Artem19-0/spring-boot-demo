package com.example.demo.controller.mvc;


import com.example.demo.model.Forecast;
import com.example.demo.service.ForecastServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller()
@RequestMapping("/weather")
public class ForecastMVCController {


    ForecastServiceImpl service;

    @Autowired
    public void setService(ForecastServiceImpl service) {
        this.service = service;
    }



    @GetMapping("/show_weather")
    public String showWeather(@RequestParam(name = "city", required = false, defaultValue="World") String city,
                              @RequestParam(name = "to") int to ,Model model) throws Exception {

        List<Forecast> forecasts = service.showWeather(city, to);
        model.addAttribute("weathers", forecasts);
        model.addAttribute("city", city);
        if (forecasts.isEmpty()){
            return null;
        }else {
            return "weather";
        }
    }
}
