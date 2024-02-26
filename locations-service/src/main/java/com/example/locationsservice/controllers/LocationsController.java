package com.example.locationsservice.controllers;

import com.example.locationsservice.entities.Location;
import com.example.locationsservice.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/locations")
public class LocationsController {
    @Autowired
    public final LocationService locationService;

    @Autowired
    public LocationsController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping
    public ResponseEntity<List<Location>> getAllLocations() {
        List<Location> locations = locationService.getAllLocations();
        return ResponseEntity.ok(locations); //200
    }

    @PostMapping
    public ResponseEntity<Location> addLocation(@RequestBody Location location) {
        Location savedLocation = locationService.saveLocation(location);
        return new ResponseEntity<>(savedLocation, HttpStatus.CREATED); //201
    }

    //TODO validation location and exception handling. Dtos.

}
