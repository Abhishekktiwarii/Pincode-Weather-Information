package com.weather.pincode.client;

import com.weather.pincode.dto.GeocodingResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j@Component
@RequiredArgsConstructor
public class GeocodingClient {

    private final RestTemplate restTemplate;

    @Value("${openweather.api.key}")
    private String apiKey;

    @Value("${openweather.api.geocoding-url}")
    private String geoUrl;

    public GeocodingResponse getLatLongByPincode(String pincode) {

        String url = UriComponentsBuilder
                .fromUriString(geoUrl)
                .queryParam("zip", pincode + ",IN")
                .queryParam("appid", apiKey)
                .build()
                .toUriString();

        return restTemplate.getForObject(url, GeocodingResponse.class);
    }
}
