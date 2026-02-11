package com.weather.pincode.service;

import com.weather.pincode.dto.GeocodeResponse;

public interface PincodeService {
    GeocodeResponse getLocationByPincode(String pincode);
}