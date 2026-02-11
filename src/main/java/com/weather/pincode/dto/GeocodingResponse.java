package com.weather.pincode.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GeocodingResponse {

    @JsonProperty("zip")
    private String zip;

    @JsonProperty("name")
    private String name;

    @JsonProperty("lat")
    private Double lat;

    @JsonProperty("lon")
    private Double lon;

    @JsonProperty("country")
    private String country;
}