package com.example.reporting.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeterValue  {

  private String date;

  private String dateAdded;

  private String physicalReference;


  private String transactionId;

  private Double meterValue;

  public String getTransactionId() {
    return transactionId;
  }
}
