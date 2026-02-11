package com.weather.pincode.controller;

import com.weather.pincode.dto.WeatherApiResponse;
import com.weather.pincode.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("/weather")
    public ResponseEntity<WeatherApiResponse> getWeather(
            @RequestParam String pincode,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        WeatherApiResponse response =
                weatherService.getWeatherByPincodeAndDate(pincode, date);

        return ResponseEntity.ok(response);
    }
}
