package com.medsavy.medsavyorderservice.controller;

import com.medsavy.medsavyorderservice.exchanges.GetOrderResponse;
import com.medsavy.medsavyorderservice.exchanges.OrderRequest;
import com.medsavy.medsavyorderservice.exchanges.OrderResponse;
import com.medsavy.medsavyorderservice.exchanges.OrderSuccessResponse;
import com.medsavy.medsavyorderservice.services.OrderService;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orm")
public class OrderController {

  @Autowired
  private OrderService orderService;

  @PostMapping("/customer/order")
  public OrderSuccessResponse placeOrder(@RequestBody OrderRequest orderRequest) {
    return orderService.placeOrder(orderRequest);
  }

  @GetMapping("/admin/orders")
  public GetOrderResponse getAllOrders(@RequestParam Integer inventoryId) {
    return orderService.getAllOrdersByInvID(inventoryId);
  }

  @GetMapping("/admin/range/orders")
  public GetOrderResponse getAllOrdersByDate(@RequestParam Integer inventoryId, @RequestParam String date1, @RequestParam String date2)
      throws ParseException {
    DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    Date d1 = format.parse(date1);
    Date d2 = format.parse(date2);
    return orderService.getAllOrdersByInvIDAndOrderDate(inventoryId, d1, d2);
  }


}
