package com.medsavy.medsavyinventorymanagementservice.exchanges;

import com.medsavy.medsavyinventorymanagementservice.entity.SalesEntity;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetSalesResponse {
  List<SalesEntity> sales = new ArrayList<>();
}
