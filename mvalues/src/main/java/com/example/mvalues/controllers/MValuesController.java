package com.example.mvalues.controllers;

import com.example.mvalues.model.MeterValue;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
public class MValuesController {

    private RestTemplate template;

    public MValuesController() {
        this.template = new RestTemplate();
    }

    String urlBase ="https://mvalues.free.beeceptor.com/metervalues"; //json meter values

    @GetMapping(path = "/metervalues", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MeterValue> getMeterValues() {
        MeterValue[] metervalues = template.getForObject(urlBase, MeterValue[].class);
        return Arrays.asList(metervalues);
    }
}