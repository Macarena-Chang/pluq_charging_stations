package com.example.reporting.controllers;

import com.example.reporting.dto.LocationDTO;
import com.example.reporting.model.Report;
import com.example.reporting.services.ReportingService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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


  @GetMapping("/report")
  @Operation(summary = "Retrieves information about a specific city",
      description = "Returns data for the city specified in the query parameter. " +
          "City names must be written with underscores (e.g., 'King's_Landing').")
  @ApiResponse(responseCode = "200", description = "Successful retrieval of report data",
      content = {@Content(mediaType = "application/json",
          schema = @Schema(implementation = Report.class))})
  public ResponseEntity<?> getLocationData(
      @Parameter(description = "The name of the city, with spaces replaced by underscores (e.g., 'New_York').",
          example = "King's_Landing") @RequestParam String city) {

    city = city.replace("_", " "); // replace underscores with spaces
    try {
      List<LocationDTO> reportData = reportingService.getLocationData(city);
      if (reportData.isEmpty()) {
        return ResponseEntity.noContent().build();
      }
      return ResponseEntity.ok(reportData);
    } catch (Exception e) {
      //  e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("An error occurred while processing your request.");
    }
  }

  @GetMapping("/report/all")
  @Operation(summary = "Retrieves information about all cities",
      description = "Returns data for all the cities available in db")
  public ResponseEntity<?> getAllLocationData() {
    try {
      //String URL_LOCATION = "https://mvalues.free.beeceptor.com/locations";
       String URL_LOCATION = "http://localhost:8090/locations";
      String result = restTemplate.getForObject(URL_LOCATION, String.class);
      ObjectMapper mapper = new ObjectMapper();
      ArrayNode arrayNode = (ArrayNode) mapper.readTree(result);
      List<String> allCities = new ArrayList<>();
      for (JsonNode jsonNode : arrayNode) {
        allCities.add(jsonNode.path("city").asText());
      }

      // Generate report for each city
      List<LocationDTO> allReportData = new ArrayList<>();
      for (String city : allCities) {
        List<LocationDTO> reportData = reportingService.getLocationData(city);
        allReportData.addAll(reportData);
      }

      if (allReportData.isEmpty()) {
        return ResponseEntity.noContent().build();
      }
      return ResponseEntity.ok(allReportData);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("An error occurred while processing your request.");
    }
  }

}


