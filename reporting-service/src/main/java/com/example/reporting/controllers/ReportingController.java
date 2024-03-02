package com.example.reporting.controllers;

import com.example.reporting.dto.ReportDTO;
import com.example.reporting.services.ReportingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class ReportingController {
    private final RestTemplate restTemplate;
    private final ReportingService reportingService;

    public ReportingController(ReportingService reportingService) {
        this.restTemplate = new RestTemplate();
        this.reportingService = reportingService;
    }

    String urlBase = "http://macalipe.33kbps.com.ar/energyPrices"; //json energy values
    String urlLocation = "https://mvalues.free.beeceptor.com/locations"; //json locations;



    @GetMapping("/report")
    public ResponseEntity<?> getLocationData(@RequestParam String city) {
        try {
            List<ReportDTO> reportData = reportingService.getLocationData(city);
            if (reportData.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(reportData);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request.");
        }
    }
}