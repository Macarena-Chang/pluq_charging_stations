package com.example.reporting.controllers;

import com.example.reporting.services.ReportingService;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReportingController {
    private final RestTemplate template;
    private final ReportingService reportingService;
    public ReportingController(ReportingService reportingService) {
        this.template = new RestTemplate();
        this.reportingService = reportingService;
    }



    private String urlBase ="http://macalipe.33kbps.com.ar/energyPrices"; //json energy values


}
