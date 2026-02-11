package com.weather.pincode.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
public class WeatherResponse {

    @JsonProperty("weather")
    private List<Weather> weather;

    @JsonProperty("main")
    private Main main;

    @JsonProperty("wind")
    private Wind wind;

    @JsonProperty("name")
    private String name;

    @Data
    @NoArgsConstructor
    public static class Weather {
        @JsonProperty("id")
        private int id;

        @JsonProperty("main")
        private String main;

        @JsonProperty("description")
        private String description;

        @JsonProperty("icon")
        private String icon;
    }

    @Data
    @NoArgsConstructor
    public static class Main {
        @JsonProperty("temp")
        private Double temp;

        @JsonProperty("feels_like")
        private Double feelsLike;

        @JsonProperty("humidity")
        private Integer humidity;

        @JsonProperty("pressure")
        private Integer pressure;
    }

    @Data
    @NoArgsConstructor
    public static class Wind {
        @JsonProperty("speed")
        private Double speed;

        @JsonProperty("deg")
        private Integer deg;
    }
}