package com.example.reporting.services;

import com.example.reporting.dto.ReportDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportingService {

    private final RestTemplate restTemplate;
    String urlLocation = "https://mvalues.free.beeceptor.com/locations"; //json locations;

    public ReportingService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public List<ReportDTO> getLocationData(String city) {
        String result = restTemplate.getForObject(urlLocation, String.class);
        ObjectMapper mapper = new ObjectMapper();
        List<ReportDTO> locationData = new ArrayList<>();
        ArrayNode arrayNode;
        try {
            arrayNode = (ArrayNode) mapper.readTree(result);
            for (JsonNode jsonNode : arrayNode) {
                String cityN = jsonNode.get("city").asText(); //get city name, should check if present in json
                if (cityN.equalsIgnoreCase(city)) {
                    int chargingSockets = 0;
                    JsonNode evsesN = jsonNode.get("evses");
                    if (evsesN != null && evsesN.isArray()) {
                        for (JsonNode evse : evsesN) {
                            JsonNode connectorsN = evse.get("connectors");
                            if (connectorsN != null && connectorsN.isArray()) {
                                chargingSockets += connectorsN.size();
                            }
                        }
                    }
                    ReportDTO reportDTO = new ReportDTO();
                    reportDTO.setCity(city);
                    reportDTO.setChargingSockets(chargingSockets);
                    locationData.add(reportDTO);

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return locationData;

    }
}