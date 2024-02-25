package com.example.locationsservice.controllers;

import com.example.locationsservice.entities.Location;
import com.example.locationsservice.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Location> getAllLocations() {
        return locationService.getAllLocations();
    }

    @PostMapping
    public Location addLocation(@RequestBody Location location) {
        return locationService.saveLocation(location);
    }

}
