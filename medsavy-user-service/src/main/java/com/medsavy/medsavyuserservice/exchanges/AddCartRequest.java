package com.medsavy.medsavyuserservice.exchanges;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddCartRequest {
  private Integer customerId;
  private Integer inventoryId;
  private String medName;
  private Integer quantity;
  private Double totalPrice;
}
