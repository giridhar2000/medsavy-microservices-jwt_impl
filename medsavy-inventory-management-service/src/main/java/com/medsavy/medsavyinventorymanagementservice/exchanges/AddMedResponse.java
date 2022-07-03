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
public class AddMedResponse {

  private Integer batchId;

  private String name;

  private Integer inventoryId;

  private Integer quantity;

  private String message;


  private Boolean success;
}
