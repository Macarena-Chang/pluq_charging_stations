package com.example.reporting.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

    @Data
    @Builder
    @AllArgsConstructor
    @RequiredArgsConstructor
    @Document(collection = "energyprices")
    public class EnergyPrice {
        private String id;
        private String country;
        private String buyVolume;
        private String sellVolume;
        private String price;
        private String currency;
        private String unit;
        private String date;
        private String timeslot;
    }

