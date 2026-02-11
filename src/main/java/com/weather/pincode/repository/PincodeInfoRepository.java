package com.weather.pincode.repository;

import com.weather.pincode.entity.PincodeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface PincodeInfoRepository
        extends JpaRepository<PincodeInfo, Long> {

    Optional<PincodeInfo> findByPincode(String pincode);
}
