package com.example.mvalues.repository;

import  com.example.mvalues.model.MeterValue;
import org.springframework.data.mongodb.repository.MongoRepository;
public interface MeterValueRepository  extends MongoRepository<MeterValue, String> {
}


