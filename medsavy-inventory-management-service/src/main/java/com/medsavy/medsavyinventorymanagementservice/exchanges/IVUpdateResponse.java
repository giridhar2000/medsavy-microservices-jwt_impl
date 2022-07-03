package com.medsavy.medsavyinventorymanagementservice.exchanges;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class IVUpdateResponse {
  private String medName;

  private Integer inventoryId;

  private Integer updatedQuantity;

  private Integer batchesUpdated;

  private String message;

  private Boolean success;

  public IVUpdateResponse(String medName, Integer inventoryId, Integer updatedQuantity,
      Integer batchesUpdated) {
    this.medName = medName;
    this.inventoryId = inventoryId;
    this.updatedQuantity = updatedQuantity;
    this.batchesUpdated = batchesUpdated;
  }


}
