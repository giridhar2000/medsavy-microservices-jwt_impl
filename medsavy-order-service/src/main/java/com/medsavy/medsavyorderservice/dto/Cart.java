package com.medsavy.medsavyorderservice.dto;

import java.util.List;
import lombok.Data;

@Data
public class Cart{
	private List<CartItem> cartItemList;
}

