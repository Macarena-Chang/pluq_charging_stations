package com.example.mvalues.controllers;

import com.example.mvalues.model.MeterValue;
import com.example.mvalues.services.MValuesService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
public class MValuesController {

    private final RestTemplate template;
    private final MValuesService mValuesService;

    public MValuesController(MValuesService mValuesService) {
        this.template = new RestTemplate();
        this.mValuesService = mValuesService;
    }

    private String urlBase ="https://mvalues.free.beeceptor.com/metervalues"; //json meter values


    @GetMapping(path = "/metervalues", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MeterValue> getMeterValues() {
        MeterValue[] metervalues = template.getForObject(urlBase, MeterValue[].class);
        return Arrays.asList(metervalues);
    }

    @PostMapping(path = "/metervalues", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public MeterValue postMeterValue(@RequestBody MeterValue meterValue) {
        return mValuesService.saveMeterValue(meterValue);
    }

    @PostMapping(path = "/metervalues/fetch-save", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MeterValue>> fetchAndSaveMeterValues() {
        MeterValue[] meterValues = template.getForObject(urlBase, MeterValue[].class);
        List<MeterValue> savedMeterValues = mValuesService.saveMeterValues(Arrays.asList(meterValues));
        return ResponseEntity.ok(savedMeterValues);
}

//TODO handle tje case where metervalue is null

}
