package com.weather.pincode.service.impl;

import com.weather.pincode.client.GeocodingClient;
import com.weather.pincode.client.WeatherClient;
import com.weather.pincode.dto.GeocodingResponse;
import com.weather.pincode.dto.WeatherApiResponse;
import com.weather.pincode.dto.WeatherResponse;
import com.weather.pincode.entity.PincodeInfo;
import com.weather.pincode.entity.WeatherInfo;
import com.weather.pincode.repository.PincodeInfoRepository;
import com.weather.pincode.repository.WeatherInfoRepository;
import com.weather.pincode.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class WeatherServiceImpl implements WeatherService {

    private final PincodeInfoRepository pincodeRepo;
    private final WeatherInfoRepository weatherRepo;
    private final GeocodingClient geocodingClient;
    private final WeatherClient weatherClient;

    @Override
    public WeatherApiResponse getWeatherByPincodeAndDate(String pincode, LocalDate date) {

        // Step 1: Check weather cache
        var cachedWeather = weatherRepo.findByPincodeAndDate(pincode, date);
        if (cachedWeather.isPresent()) {
            PincodeInfo pincodeInfo = pincodeRepo.findByPincode(pincode).get();
            return mapToResponse(pincodeInfo, cachedWeather.get(), true);
        }

        // Step 2: Get or fetch pincode lat/long
        PincodeInfo pincodeInfo = pincodeRepo.findByPincode(pincode)
                .orElseGet(() -> {
                    GeocodingResponse geo = geocodingClient.getLatLongByPincode(pincode);

                    PincodeInfo info = new PincodeInfo();
                    info.setPincode(pincode);
                    info.setLatitude(geo.getLat());
                    info.setLongitude(geo.getLon());
                    info.setCity(geo.getName());
                    info.setCountry(geo.getCountry());

                    return pincodeRepo.save(info);
                });

        // Step 3: Call Weather API
        WeatherResponse weatherResponse =
                weatherClient.getWeatherByLatLong(
                        pincodeInfo.getLatitude(),
                        pincodeInfo.getLongitude());

        // Step 4: Save weather
        WeatherInfo weatherInfo = new WeatherInfo();
        weatherInfo.setPincode(pincode);
        weatherInfo.setDate(date);
        weatherInfo.setTemperature(weatherResponse.getMain().getTemp());
        weatherInfo.setFeelsLike(weatherResponse.getMain().getFeelsLike());
        weatherInfo.setHumidity(weatherResponse.getMain().getHumidity());
        weatherInfo.setPressure(weatherResponse.getMain().getPressure());
        weatherInfo.setDescription(weatherResponse.getWeather().get(0).getDescription());
        weatherInfo.setIcon(weatherResponse.getWeather().get(0).getIcon());
        weatherInfo.setWindSpeed(weatherResponse.getWind().getSpeed());
        weatherInfo.setWindDeg(weatherResponse.getWind().getDeg());

        WeatherInfo saved = weatherRepo.save(weatherInfo);

        return mapToResponse(pincodeInfo, saved, false);
    }

    private WeatherApiResponse mapToResponse(
            PincodeInfo pincodeInfo,
            WeatherInfo weatherInfo,
            boolean cached) {

        return WeatherApiResponse.builder()
                .pincode(pincodeInfo.getPincode())
                .date(weatherInfo.getDate())
                .latitude(pincodeInfo.getLatitude())
                .longitude(pincodeInfo.getLongitude())
                .city(pincodeInfo.getCity())
                .temperature(weatherInfo.getTemperature())
                .feelsLike(weatherInfo.getFeelsLike())
                .humidity(weatherInfo.getHumidity())
                .pressure(weatherInfo.getPressure())
                .description(weatherInfo.getDescription())
                .icon(weatherInfo.getIcon())
                .windSpeed(weatherInfo.getWindSpeed())
                .windDeg(weatherInfo.getWindDeg())
                .cached(cached)
                .build();
    }
}
