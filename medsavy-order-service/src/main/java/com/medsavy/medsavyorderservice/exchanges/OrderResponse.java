package com.medsavy.medsavyorderservice.exchanges;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderResponse {
  private Boolean success;
  private String medName;

  private Integer inventoryId;

  private int quantityOrdered;

  private Date orderDate;

  private Double totalAmount;

  private String message;
}
