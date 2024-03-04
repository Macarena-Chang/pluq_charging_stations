package com.example.locationsservice.services;

import com.example.locationsservice.entities.Location;
import java.util.Arrays;
import java.util.List;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
@Data
public class LocationFetchService {
  private  final RestTemplate restTemplate;
  private static final String urlAPI = "https://mvalues.free.beeceptor.com/locations";

  @Autowired
  public LocationFetchService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

    public List<Location> fetchLocationData() {
      ResponseEntity<Location[]> responseEntity = restTemplate.getForEntity(urlAPI, Location[].class);
      if (responseEntity.getStatusCode() == HttpStatus.OK) {
        return Arrays.asList(responseEntity.getBody()); //TODO could be null
      } else {
        throw  new IllegalArgumentException("Failed to fetch location data");
      }
    }
  }
