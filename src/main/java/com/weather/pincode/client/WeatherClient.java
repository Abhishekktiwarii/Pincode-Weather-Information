package com.weather.pincode.client;

import com.weather.pincode.dto.WeatherResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j@Component
@RequiredArgsConstructor
public class WeatherClient {

    private final RestTemplate restTemplate;

    @Value("${openweather.api.key}")
    private String apiKey;

    @Value("${openweather.api.weather-url}")
    private String weatherUrl;

    public WeatherResponse getWeatherByLatLong(Double lat, Double lon) {

        String url = UriComponentsBuilder
                .fromUriString(weatherUrl)
                .queryParam("lat", lat)
                .queryParam("lon", lon)
                .queryParam("appid", apiKey)
                .queryParam("units", "metric")
                .build()
                .toUriString();

        return restTemplate.getForObject(url, WeatherResponse.class);
    }
}
