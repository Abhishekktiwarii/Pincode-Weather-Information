package com.weather.pincode.service.impl;

import com.weather.pincode.client.GeocodingClient;
import com.weather.pincode.dto.GeocodeResponse;
import com.weather.pincode.dto.GeocodingResponse;  // ✅ FIXED: Added this import
import com.weather.pincode.entity.PincodeInfo;
import com.weather.pincode.repository.PincodeInfoRepository;
import com.weather.pincode.service.PincodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PincodeServiceImpl implements PincodeService {

    private final PincodeInfoRepository pincodeRepo;
    private final GeocodingClient geocodingClient;

    @Override
    public GeocodeResponse getLocationByPincode(String pincode) {
        log.info("Processing location request for pincode: {}", pincode);

        // Check cache first
        var cachedPincode = pincodeRepo.findByPincode(pincode);
        if (cachedPincode.isPresent()) {
            log.info("Cache hit for pincode: {}", pincode);
            return mapToGeocodeResponse(cachedPincode.get(), true);
        }

        // Cache miss - call Geocoding API
        log.info("Cache miss for pincode: {} - calling Geocoding API", pincode);
        GeocodingResponse geocodingResponse = geocodingClient.getLatLongByPincode(pincode);

        // Save to database
        var pincodeInfo = createPincodeInfo(pincode, geocodingResponse);
        pincodeRepo.save(pincodeInfo);
        log.info("Pincode {} saved to database", pincode);

        return mapToGeocodeResponse(pincodeInfo, false);
    }

    private PincodeInfo createPincodeInfo(String pincode, GeocodingResponse response) {
        PincodeInfo info = new PincodeInfo();
        info.setPincode(pincode);
        info.setLatitude(response.getLat());
        info.setLongitude(response.getLon());
        info.setCity(response.getName());
        info.setCountry(response.getCountry());
        return info;
    }

    private GeocodeResponse mapToGeocodeResponse(PincodeInfo info, boolean cached) {
        return GeocodeResponse.builder()
                .pincode(info.getPincode())
                .latitude(info.getLatitude())
                .longitude(info.getLongitude())
                .city(info.getCity())
                .country(info.getCountry())
                .cached(cached)
                .build();
    }
}