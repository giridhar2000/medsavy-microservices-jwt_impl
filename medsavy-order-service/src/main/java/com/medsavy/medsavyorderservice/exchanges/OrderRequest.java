package com.medsavy.medsavyorderservice.exchanges;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

  private Integer customerId;

  private Integer inventoryId;

  private String medName;

  private Integer quantity;
}
