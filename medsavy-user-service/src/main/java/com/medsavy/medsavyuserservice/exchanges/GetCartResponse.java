package com.medsavy.medsavyuserservice.exchanges;

import com.medsavy.medsavyuserservice.dtos.CartItem;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetCartResponse {

  private List<CartItem> cartItemList = new ArrayList<>();
}
