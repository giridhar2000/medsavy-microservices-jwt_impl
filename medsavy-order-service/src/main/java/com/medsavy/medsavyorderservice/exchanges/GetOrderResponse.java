package com.medsavy.medsavyorderservice.exchanges;

import com.medsavy.medsavyorderservice.dto.Order;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetOrderResponse {
  private List<Order> orderList = new ArrayList<>();
}
