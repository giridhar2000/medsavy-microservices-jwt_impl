package com.medsavy.medsavyinventorymanagementservice.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Sales")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer salesId;
  private String transaction;
  private String medName;
  private Integer customerId;
  private Integer sellerId;
  private Integer totalQuantity;
  private Double transactionAmount;

  public SalesEntity(String transaction, String medName, Integer customerId,
      Integer sellerId, Integer totalQuantity, Double transactionAmount) {
    this.transaction = transaction;
    this.medName = medName;
    this.customerId = customerId;
    this.sellerId = sellerId;
    this.totalQuantity = totalQuantity;
    this.transactionAmount = transactionAmount;
  }
}
