package com.medsavy.medsavyuserservice.exchanges;

import lombok.Data;

@Data
public class IVCreateResponse{
	private boolean success;
	private int inventoryId;
	private String message;
}