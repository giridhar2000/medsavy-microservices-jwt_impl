package com.medsavy.medsavyinventorymanagementservice.exchanges;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IVUpdateRequest {

  private String medName;
  private Integer quantity;
  private Integer customerId;
}
