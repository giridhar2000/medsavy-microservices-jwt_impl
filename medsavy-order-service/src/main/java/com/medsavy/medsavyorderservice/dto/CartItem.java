package com.medsavy.medsavyorderservice.dto;

import lombok.Data;

@Data
public class CartItem {
  private String medName;
  private int quantity;
  private double totalPrice;
  private int customerId;
  private int inventoryId;
}