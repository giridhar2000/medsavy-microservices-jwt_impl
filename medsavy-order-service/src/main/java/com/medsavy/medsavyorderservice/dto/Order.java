package com.medsavy.medsavyorderservice.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
  private Integer orderId;

  private String medName;

  private int quantityOrdered;

  private Date orderDate;

  private Double totalAmount;

  private Integer customerId;
}
