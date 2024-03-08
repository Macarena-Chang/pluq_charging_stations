package com.example.reporting.services;

import com.example.reporting.dto.LocationDTO;
import com.example.reporting.model.MeterValue;
import com.example.reporting.model.SessionInfo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ReportingService {

  private static final Logger logger = LoggerFactory.getLogger(ReportingService.class);
  //private static final String URL_LOCATION = "https://mvalues.free.beeceptor.com/locations";
  private static final String URL_LOCATION = "http://localhost:8090/locations";
  //private static final String URL_METERVALUES = "http://macalipe.33kbps.com.ar/meterValues";
  private static final String URL_METERVALUES = "http://localhost:8100/metervalues";
  private final RestTemplate restTemplate;


    public static String getUrlMetervalues() {
      return URL_METERVALUES;
    }
    public static String getUrlLocation() {
      return URL_LOCATION;
    }


  public ReportingService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public ArrayList<MeterValue> getMeterValuesList() {
    String result = restTemplate.getForObject(URL_METERVALUES, String.class);
    ObjectMapper mapper = new ObjectMapper();
    ArrayList<MeterValue> meterValueList = new ArrayList<>();
    try {
      meterValueList = mapper.readValue(result, new TypeReference<ArrayList<MeterValue>>() {
      });
    } catch (Exception e) {
      logger.error("An error occurred while processing your request", e);
    }
    return meterValueList;
  }

  public List<LocationDTO> getLocationData(String city) {
    String result = restTemplate.getForObject(URL_LOCATION, String.class);
    ObjectMapper mapper = new ObjectMapper();
    List<LocationDTO> locationData = new ArrayList<>();
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

  public int processLocationNode(JsonNode jsonNode, String city, List<LocationDTO> locationData)
      throws Exception {
    String cityN = jsonNode.path("city").asText();
    LocationDTO locationDTO = null;
    ArrayList<String> uids;
    if (cityN.equalsIgnoreCase(city)) {
      int chargingSockets = countChargingSockets(jsonNode);
      locationDTO = new LocationDTO();
      locationDTO.setCity(city);
      locationDTO.setChargingSockets(chargingSockets);

      JsonNode evses = jsonNode.path("evses");
      uids = new ArrayList<>();

      for (JsonNode evse : evses) {
        String uid = evse.path("uid").asText();
        uids.add(uid);
      }
      locationDTO.setUids(uids);
      ArrayList<MeterValue> metervalues = getMeterValuesList();
      metervalues = filterMeterValuesByUids(metervalues, uids);
      locationDTO.setMeterValuesList(metervalues);

      // Extract unique transaction IDs and set sessions and sessionsList
      Set<String> uniqueTransactionIds = getUniqueTransactionIds(metervalues);
      locationDTO.setSessions(uniqueTransactionIds.size());
      locationDTO.setSessionsList(new ArrayList<>(uniqueTransactionIds));
      // charged per session
      Map<String, SessionInfo> totalchargedpsession = new HashMap<>();
      totalchargedpsession = calculateChargedPerSession(metervalues, uniqueTransactionIds);
      locationDTO.setChargedKwhPerSession(totalchargedpsession);
      double totalKwhCharged;
      ArrayList<Double> valuesList = totalchargedpsession.values().stream()
          .map(SessionInfo::getTotalCharged)
          .collect(Collectors.toCollection(ArrayList::new));
      logger.info("Values List: {}", valuesList);
      totalKwhCharged = valuesList.stream()
          .mapToDouble(Double::doubleValue) // Convert the Doubles to double primitives.
          .sum();
      logger.info("Total Kwh charged: {}", totalKwhCharged);
      locationDTO.setTotalKwhCharged(totalKwhCharged);

      // charged per socket , uid
      Map<String, Double> chargedPerPhysicalReference = calculateChargedPerPhysicalReference(
          totalchargedpsession);
      locationDTO.setChargedPerSocket(chargedPerPhysicalReference);

      // charged per socket per day
      TreeMap<String, TreeMap<String, Double>> chargedPerDayPerSocket = calculateChargedPerDayPerSocket(
          totalchargedpsession);
      locationDTO.setChargedPerSocketPerDay(chargedPerDayPerSocket);
      logger.info("Charged per day per socket: {}", chargedPerDayPerSocket);
      locationData.add(locationDTO);

      //Generate report
     // genReport(city, locationDTO);

    }

    return locationData.size();
  }


/*
  public Report genReport(String city, LocationDTO locationDTO) {
    Report report = new Report();
    report.setLocationName(city);
    report.setAmountOfChargingSockets(locationDTO.getChargingSockets());
    report.setTotalAmountOfKwhCharged(locationDTO.getTotalKwhCharged());
    report.setAmountOfChargingSessions(locationDTO.getSessions());
    report.setAmountOfKwhChargedPerSocket(locationDTO.getChargedPerSocket().values().stream()
        .mapToDouble(Double::doubleValue)
        .sum());
    report.setAmountOfKwhChargedPerSession(
        locationDTO.getChargedKwhPerSession().values().stream()  //charged per session
            .mapToDouble(SessionInfo::getTotalCharged)
            .sum());
    report.setAmountOfKwhChargedPerDayPerSocket(locationDTO.getChargedPerSocketPerDay());

    return report;
  }
*/


  public Integer countChargingSockets(JsonNode locationNode) {
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

  public ArrayList<MeterValue> filterMeterValuesByUids(ArrayList<MeterValue> meterValues,
      ArrayList<String> uids) {
    return meterValues.stream()
        .filter(mv -> uids.contains(mv.getPhysicalReference()))
        .collect(Collectors.toCollection(ArrayList::new));
  }

  public Set<String> getUniqueTransactionIds(List<MeterValue> meterValuesList) {
    Set<String> uniqueTransactionIds = new HashSet<>();
    for (MeterValue meterValue : meterValuesList) {
      uniqueTransactionIds.add(meterValue.getTransactionId());
    }
    return uniqueTransactionIds;
  }

  public Map<String, SessionInfo> calculateChargedPerSession(ArrayList<MeterValue> meterValuesList,
      Set<String> sessionsList) {
    if (meterValuesList == null || meterValuesList.isEmpty() || sessionsList == null
        || sessionsList.isEmpty()) {
      return Collections.emptyMap();
    }

    Map<String, SessionInfo> totalChargedPerSession = new HashMap<>();

    for (String sessionId : sessionsList) {
      List<MeterValue> meterValuesForSession = meterValuesList.stream()
          .filter(meterValue -> meterValue.getTransactionId().equals(sessionId))
          .sorted(Comparator.comparing(MeterValue::getDate))
          .toList();

      if (!meterValuesForSession.isEmpty()) {
        double firstMeterValue = meterValuesForSession.get(0).getMeterValue();
        double lastMeterValue = meterValuesForSession.get(meterValuesForSession.size() - 1)
            .getMeterValue();
        String physicalReference = meterValuesForSession.get(0).getPhysicalReference();
        String date = meterValuesForSession.get(0).getDate();
        totalChargedPerSession.put(sessionId,
            new SessionInfo(lastMeterValue - firstMeterValue, physicalReference, date));
      }
    }

    return totalChargedPerSession;
  }

  public Map<String, Double> calculateChargedPerPhysicalReference(
      Map<String, SessionInfo> chargedKwhPerSession) {
    Map<String, Double> chargedPerPhysicalReference = new HashMap<>();

    for (SessionInfo sessionInfo : chargedKwhPerSession.values()) {
      String physicalReference = sessionInfo.getPhysicalReference();
      double totalCharged = sessionInfo.getTotalCharged();

      chargedPerPhysicalReference.merge(physicalReference, totalCharged, Double::sum);
    }

    return chargedPerPhysicalReference;
  }

  // calculate kWh charged per day per socket
  public TreeMap<String, TreeMap<String, Double>> calculateChargedPerDayPerSocket(
      Map<String, SessionInfo> chargedKwhPerSession) {
    return chargedKwhPerSession.values().stream()
        .collect(Collectors.groupingBy(SessionInfo::getDate,
            TreeMap::new, //so we can sort it by date
            Collectors.groupingBy(SessionInfo::getPhysicalReference,
                TreeMap::new, // sort it by identifier
                Collectors.summingDouble(SessionInfo::getTotalCharged))));
  }


}