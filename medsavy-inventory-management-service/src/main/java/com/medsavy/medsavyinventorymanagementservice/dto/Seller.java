package com.medsavy.medsavyinventorymanagementservice.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seller {

  private int totalQuantity;
  private List<Batch> batches = new ArrayList<>();


  public void addBatch(Batch batch) {
    batches.add(batch);
  }


}
