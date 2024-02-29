package com.example.reporting.controllers;

import com.example.reporting.services.ReportingService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ReportingController {
    private final RestTemplate restTemplate;
    private final ReportingService reportingService;

    public ReportingController(ReportingService reportingService) {
        this.restTemplate = new RestTemplate();
        this.reportingService = reportingService;
    }

    private String urlBase = "http://macalipe.33kbps.com.ar/energyPrices"; //json energy values




}