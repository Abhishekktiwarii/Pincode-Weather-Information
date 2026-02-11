package com.weather.pincode.service;

import com.weather.pincode.dto.WeatherApiResponse;
import java.time.LocalDate;

public interface WeatherService {
    WeatherApiResponse getWeatherByPincodeAndDate(String pincode, LocalDate date);
}
