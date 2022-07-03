package com.medsavy.medsavyorderservice.exchanges;

import lombok.Data;

@Data
public class CartClearResponse{
	private Integer deletedRows;
	private Boolean success;
}