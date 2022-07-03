package com.medsavy.medsavyinventorymanagementservice.exchanges;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteMedResponse {

  private String medName;
  private Integer batchId;
  private Integer quantity;
  private String message;
}
