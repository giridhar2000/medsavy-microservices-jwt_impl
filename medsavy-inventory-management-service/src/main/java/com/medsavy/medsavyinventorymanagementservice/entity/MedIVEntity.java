package com.medsavy.medsavyinventorymanagementservice.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "MedicineInventory")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedIVEntity {



  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer batchId;

  private Integer inventoryId;

  private String name;

  private String type;

  private Double price;

  private String expiryDate;

  private Integer quantity;

  private String transaction;

  public MedIVEntity(Integer inventoryId, String name, String type, Double price,
      String expiryDate, Integer quantity, String transaction) {

    this.inventoryId = inventoryId;
    this.name = name;
    this.type = type;
    this.price = price;
    this.expiryDate = expiryDate;
    this.quantity = quantity;
    this.transaction = transaction;
  }

  public void increaseQuantity(Integer quantity) {
    this.quantity = this.quantity + quantity;
  }

  public Boolean decreaseQuantity(Integer quantity) {
    if(this.quantity != 0 && this.quantity >= quantity) {
      this.quantity = this.quantity - quantity;
      return true;
    }
    return false;
  }

  public void updatePrice(Double price) {
    this.price = price;
  }
}
