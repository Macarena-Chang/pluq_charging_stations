package com.example.mvalues.services;

import com.example.mvalues.model.MeterValue;
import com.example.mvalues.repository.MeterValueRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MValuesService {
    private final MeterValueRepository meterValueRepository;

    public MValuesService(MeterValueRepository meterValueRepository) {
        this.meterValueRepository = meterValueRepository;
    }


    /**
     * stores a single MeterValue object to the database.
     */
    public MeterValue saveMeterValue(MeterValue meterValue) {
        return meterValueRepository.save(meterValue);
    }

    /**
     * stores a LIST of MeterValue objects to the database. (originally thought about this to be able to fetch json and store all entries in db)
     */
    public List<MeterValue> saveMeterValues(List<MeterValue> meterValues) {
        return meterValueRepository.saveAll(meterValues);
    }
}