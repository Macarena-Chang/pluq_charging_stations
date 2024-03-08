package com.example.reporting.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.example.reporting.dto.LocationDTO;
import com.example.reporting.model.MeterValue;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

public class ReportingServiceTest {

  @Mock
  private RestTemplate restTemplate;

  private ReportingService reportingService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    reportingService = new ReportingService(restTemplate);
  }

  //get metervaluesList
  @Test
  public void getMeterValuesList_returnsMeterValues() {
    String mockResponse = "[{\"transactionId\":\"1\",\"physicalReference\":\"A\",\"meterValue\":10.0,\"date\":\"2022-01-01\"}]";
    when(restTemplate.getForObject(ReportingService.getUrlMetervalues(), String.class)).thenReturn(
        mockResponse);

    ArrayList<MeterValue> meterValues = reportingService.getMeterValuesList();

    assertEquals(1, meterValues.size());
    assertEquals("1", meterValues.get(0).getTransactionId());
    assertEquals("A", meterValues.get(0).getPhysicalReference());
    assertEquals(10.0, meterValues.get(0).getMeterValue());
    assertEquals("2022-01-01", meterValues.get(0).getDate());
  }

  @Test
  public void getMeterValuesList_returnsEmptyList_whenNoMeterValues() {
    String mockResponse = "[]";
    when(restTemplate.getForObject(ReportingService.getUrlMetervalues(), String.class)).thenReturn(
        mockResponse);

    ArrayList<MeterValue> meterValues = reportingService.getMeterValuesList();

    assertEquals(0, meterValues.size());
  }

  @Test
  public void getMeterValuesList_returnsEmptyList_whenExceptionOccurs() {
    when(restTemplate.getForObject(ReportingService.getUrlMetervalues(), String.class)).thenReturn(
        null);

    ArrayList<MeterValue> meterValues = reportingService.getMeterValuesList();

    assertEquals(0, meterValues.size());
  }

  // countChargingSockets
  @Test
  public void countChargingSockets_returnsCorrectCount() throws Exception {
    String mockResponse = "{\"evses\":[{\"connectors\":[{},{}]},{\"connectors\":[{}]}]}";
    JsonNode locationNode = new ObjectMapper().readTree(mockResponse);

    int result = reportingService.countChargingSockets(locationNode);

    assertEquals(3, result);
  }

  @Test
  public void countChargingSockets_returnsZero_whenNoEvses() throws Exception {
    String mockResponse = "{\"evses\":[]}";
    JsonNode locationNode = new ObjectMapper().readTree(mockResponse);

    int result = reportingService.countChargingSockets(locationNode);

    assertEquals(0, result);
  }

  @Test
  public void countChargingSockets_returnsZero_whenNoConnectors() throws Exception {
    String mockResponse = "{\"evses\":[{\"connectors\":[]},{\"connectors\":[]}]}";
    JsonNode locationNode = new ObjectMapper().readTree(mockResponse);

    int result = reportingService.countChargingSockets(locationNode);

    assertEquals(0, result);
  }

  // filterMeterValuesByUids
  @Test
  public void filterMeterValuesByUids_returnsFilteredMeterValues() {
    // Prepare  data
    ArrayList<MeterValue> meterValues = new ArrayList<>(Arrays.asList(
        new MeterValue("2022-01-01", "2022-01-01", "A", "1", 10.0),
        new MeterValue("2022-01-02", "2022-01-02", "B", "2", 20.0),
        new MeterValue("2022-01-03", "2022-01-03", "C", "3", 30.0)
    ));
    ArrayList<String> uids = new ArrayList<>(Arrays.asList("A", "C"));

    // Call the method under test
    ArrayList<MeterValue> filteredMeterValues = reportingService.filterMeterValuesByUids(
        meterValues, uids);

    // Assert the results
    assertEquals(2, filteredMeterValues.size());
    assertEquals("A", filteredMeterValues.get(0).getPhysicalReference());
    assertEquals("C", filteredMeterValues.get(1).getPhysicalReference());
  }

  // GetUniqueTransactionIds
  @Test
  public void getUniqueTransactionIds_returnsUniqueIds() {
    // Prepare test data
    List<MeterValue> meterValuesList = Arrays.asList(
        new MeterValue("2022-01-01", "2022-01-01", "A", "1", 10.0),
        new MeterValue("2022-01-02", "2022-01-02", "B", "2", 20.0),
        new MeterValue("2022-01-03", "2022-01-03", "C", "1", 30.0) // Duplicate transactionId "1"
    );

    // Call the method under test
    Set<String> uniqueTransactionIds = reportingService.getUniqueTransactionIds(meterValuesList);

    // Assert the results
    assertEquals(2, uniqueTransactionIds.size(), "There should be only 2 unique transaction IDs.");
    assertTrue(uniqueTransactionIds.contains("1"), "The set should contain transaction ID '1'.");
    assertTrue(uniqueTransactionIds.contains("2"), "The set should contain transaction ID '2'.");
  }

  //GetLocationData
  @Test
  public void getLocationData_returnsLocationDataForCity() {
    // Prepare test data
    String city = "TestCity";
    String mockResponse = "[{\"city\":\"TestCity\",\"otherField\":\"value\"}]";
    when(restTemplate.getForObject(ReportingService.getUrlLocation(), String.class)).thenReturn(
        mockResponse);

    // Call the method under test
    List<LocationDTO> locationData = reportingService.getLocationData(city);

    // Assert the results
    assertEquals(1, locationData.size(), "There should be 1 location data entry for the city.");
    assertEquals(city, locationData.get(0).getCity(), "The city should match the requested city.");
  }

  //Process Location
  @Test
  public void processLocationNode_addsLocationDataForMatchingCity() throws Exception {
    // Prepare  data
    String city = "TestCity";
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode jsonNode = mapper.createObjectNode();
    jsonNode.put("city", city);
    jsonNode.set("evses", mapper.createArrayNode()); // Assuming evses is an array

    List<LocationDTO> locationData = new ArrayList<>();

    // Call  method
    int sizeBefore = locationData.size();
    int resultSize = reportingService.processLocationNode(jsonNode, city, locationData);

    // Assert the results
    assertEquals(sizeBefore + 1, resultSize, "The location data list should have one more entry.");
    assertEquals(1, locationData.size(), "The location data list should have one entry.");
    LocationDTO locationDTO = locationData.get(0);
    assertEquals(city, locationDTO.getCity(),
        "The city in the LocationDTO should match the input city.");
  }

}


