package com.medsavy.medsavyinventorymanagementservice.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Medicine {

  private String name;

  private String type;

  private Double price;

//  private int totalQty;

//  private List<Batch> batch = new ArrayList<>();

  Map<Integer, Seller> sellers = new ConcurrentHashMap<>();

  public Medicine(String name, String type, Double price) {
    this.name = name;
    this.type = type;
    this.price = price;
  }

//  public void addBatch(Batch b) {
//    batch.add(b);
//  }

  public void addBatchToIVMap(Integer iv, Batch batch) {
    if(sellers.containsKey(iv)) {
      Seller sellerBatches = sellers.get(iv);
      sellerBatches.setTotalQuantity(sellerBatches.getTotalQuantity() + batch.getQuantity());
      sellerBatches.addBatch(batch);

    } else {
      Seller seller = new Seller();
      seller.addBatch(batch);
      seller.setTotalQuantity(seller.getTotalQuantity() + batch.getQuantity());
      sellers.put(iv, seller);
    }
  }
}
