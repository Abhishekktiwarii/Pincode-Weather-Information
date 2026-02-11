package com.weather.pincode.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GeocodeResponse {
    private String pincode;
    private Double latitude;
    private Double longitude;
    private String city;
    private String country;
    private boolean cached;
}