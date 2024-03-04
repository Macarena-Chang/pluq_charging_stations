package com.example.locationsservice.services;

import com.example.locationsservice.entities.Location;
import com.example.locationsservice.repositories.LocationRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationService {

  private final LocationRepository locationRepository;
  private final LocationFetchService locationFetchService;

  @Autowired
  public LocationService(LocationRepository locationRepository,
      LocationFetchService locationFetchService) {
    this.locationRepository = locationRepository;
    this.locationFetchService = locationFetchService;
  }


  //retrieve all location data from an external source
  public List<Location> getAllLocationData() {
    List<Location> locationData =  locationRepository.findAll();
    return locationData;
  }

  public List<Location> fetchLocationData() {
    try {
      List<Location> locationData = locationFetchService.fetchLocationData();
      return locationData;
    } catch (Exception e) {
      return null;
    }
  }


  // retrieve all location data from db
  public List<Location> getLocationData() {
    return locationRepository.findAll();
  }


  public Location saveLocation(Location location) {
    return locationRepository.save(location);
  }

}
