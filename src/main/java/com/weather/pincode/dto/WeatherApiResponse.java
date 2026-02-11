package com.weather.pincode.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
public class WeatherApiResponse {
    private String pincode;
    private LocalDate date;
    private Double latitude;
    private Double longitude;
    private String city;
    private Double temperature;
    private Double feelsLike;
    private Integer humidity;
    private Integer pressure;
    private String description;
    private String icon;
    private Double windSpeed;
    private Integer windDeg;
    private boolean cached;
}