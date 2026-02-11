package com.weather.pincode.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "weather_cache")
@Data
public class WeatherInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 6)
    private String pincode;

    @Column(name = "weather_date", nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private Double temperature;

    @Column(name = "feels_like", nullable = false)
    private Double feelsLike;

    @Column(nullable = false)
    private Integer humidity;

    @Column(nullable = false)
    private Integer pressure;

    @Column(nullable = false, length = 100)
    private String description;

    @Column(nullable = false, length = 10)
    private String icon;

    @Column(name = "wind_speed", nullable = false)
    private Double windSpeed;

    @Column(name = "wind_deg", nullable = false)
    private Integer windDeg;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}