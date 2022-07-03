package com.medsavy.medsavyinventorymanagementservice.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Batch {

  private Integer batchId;
  private Integer inventoryId;
  private String expiryDate;
  private Integer quantity;
}
