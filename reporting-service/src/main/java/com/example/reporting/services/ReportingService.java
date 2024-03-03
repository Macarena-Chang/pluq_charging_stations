package com.example.reporting.services;

import com.example.reporting.dto.ReportDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ReportingService {

    private static final Logger logger = LoggerFactory.getLogger(ReportingService.class);
    private final RestTemplate restTemplate;
    private static final String URL_LOCATION = "https://mvalues.free.beeceptor.com/locations";

    public ReportingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<ReportDTO> getLocationData(String city) {
        String result = restTemplate.getForObject(URL_LOCATION, String.class);
        ObjectMapper mapper = new ObjectMapper();
        List<ReportDTO> locationData = new ArrayList<>();
        try {
            ArrayNode arrayNode = (ArrayNode) mapper.readTree(result);
            for (JsonNode jsonNode : arrayNode) {
                processLocationNode(jsonNode, city, locationData);
            }
        } catch (Exception e) {
            logger.error("An error occurred while processing your request for city: {}", city, e);
        }
        return locationData;
    }

    private void processLocationNode(JsonNode jsonNode, String city, List<ReportDTO> locationData) {
        String cityN = jsonNode.path("city").asText();
        if (cityN.equalsIgnoreCase(city)) {
            int chargingSockets = countChargingSockets(jsonNode);
            ReportDTO reportDTO = new ReportDTO();
            reportDTO.setCity(city);
            reportDTO.setChargingSockets(chargingSockets);
            locationData.add(reportDTO);
        }
    }

    private int countChargingSockets(JsonNode locationNode) {
        int chargingSockets = 0;
        JsonNode evsesN = locationNode.path("evses");
        if (evsesN.isArray()) {
            for (JsonNode evse : evsesN) {
                JsonNode connectorsN = evse.path("connectors");
                if (connectorsN.isArray()) {
                    chargingSockets += connectorsN.size();
                }
            }
        }
        return chargingSockets;
    }
}
