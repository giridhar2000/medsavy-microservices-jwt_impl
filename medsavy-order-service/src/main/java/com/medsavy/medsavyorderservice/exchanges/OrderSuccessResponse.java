package com.medsavy.medsavyorderservice.exchanges;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
public class OrderSuccessResponse{
	private double totalAmount;
	private List<String> itemsOrdered = new ArrayList<>();
	private Map<Integer, String> orderIds = new HashMap<>();
	private String orderDate;
	private Map<String, String> failedOrders = new HashMap<>();
}