package com.example.locationsservice.repositories;
import com.example.locationsservice.models.Location;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LocationRepository extends MongoRepository<Location, String> {

}
