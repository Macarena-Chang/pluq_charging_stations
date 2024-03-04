package com.example.locationsservice.controllers;

import com.example.locationsservice.entities.Location;
 import com.example.locationsservice.services.LocationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
 public class LocationsController {

  private final LocationService locationService;
  private final RestTemplate restTemplate;



    @Autowired
    public LocationsController( LocationService locationService) {
        this.locationService = locationService;
      this.restTemplate = new RestTemplate();
    }

  // fetch ALL location data (FROM DB FIRST, THEN FROM API IF NEEDED)
  @GetMapping("/locations")
  public ResponseEntity<List<Location>> getAllLocations() {
    try {
      List<Location> locationsData = locationService.getLocationData();

      if (locationsData.isEmpty()) {

        locationsData = locationService.fetchLocationData();
        // If still no data is found, return no content
        if (locationsData.isEmpty()) {
          return ResponseEntity.noContent().build();
        }
      }

      return ResponseEntity.ok(locationsData);
    } catch (Exception e) {

      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

    }
  }
  @PostMapping
  public ResponseEntity<Location> addLocation(@RequestBody Location location) {
    Location savedLocation = locationService.saveLocation(location);
    return new ResponseEntity<>(savedLocation, HttpStatus.CREATED); //201
  }
}

