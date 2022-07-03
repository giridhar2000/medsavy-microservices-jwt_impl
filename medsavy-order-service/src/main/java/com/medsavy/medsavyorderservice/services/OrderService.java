package com.medsavy.medsavyorderservice.services;

import static java.util.stream.Collectors.toList;

import com.medsavy.medsavyorderservice.dto.Cart;
import com.medsavy.medsavyorderservice.dto.CartItem;
import com.medsavy.medsavyorderservice.dto.Order;
import com.medsavy.medsavyorderservice.entity.JwtEntity;
import com.medsavy.medsavyorderservice.entity.OrderEntity;
import com.medsavy.medsavyorderservice.exchanges.CartClearRequest;
import com.medsavy.medsavyorderservice.exchanges.CartClearResponse;
import com.medsavy.medsavyorderservice.exchanges.GetOrderResponse;
import com.medsavy.medsavyorderservice.exchanges.IVUpdateResponse;
import com.medsavy.medsavyorderservice.exchanges.OrderRequest;
import com.medsavy.medsavyorderservice.exchanges.OrderResponse;
import com.medsavy.medsavyorderservice.exchanges.OrderSuccessResponse;
import com.medsavy.medsavyorderservice.repository.AuthRepository;
import com.medsavy.medsavyorderservice.repository.OrderRepository;
import com.netflix.discovery.converters.Auto;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class OrderService {

  @Autowired
  private RestTemplate restTemplate;

  private ModelMapper modelMapper = new ModelMapper();

  @Autowired
  private AuthRepository authRepository;

  @Autowired
  private OrderRepository orderRepository;

  public OrderSuccessResponse placeOrder(OrderRequest orderRequest) {
    String token = "Bearer ";
    JwtEntity jwtEntity = authRepository.findJwtEntityByUserId(orderRequest.getCustomerId());
     token = token + jwtEntity.getToken();

    HttpHeaders cartHeaders = new HttpHeaders();
    cartHeaders.set("Authorization", token);
    cartHeaders.set("user", jwtEntity.getUsername());
    HttpEntity<Void> requestEntity = new HttpEntity<>(cartHeaders);

    ResponseEntity<Cart> cart = restTemplate.exchange(
        "http://localhost:8080/api/v1/user/customer/cart/items?customerId={id}",
        HttpMethod.GET, requestEntity, Cart.class, orderRequest.getCustomerId());

//    Cart cart = restTemplate.getForObject(
//        "http://localhost:8080/api/v1/user/customer/cart/items?customerId={id}",
//        Cart.class,
//        orderRequest.getCustomerId()
//    );

    List<CartItem> cartItemList = cart.getBody().getCartItemList();
    List<IVUpdateResponse> ivUpdateResponses = new ArrayList<>();
    List<OrderEntity> orderEntities = new ArrayList<>();
    String finalToken = token;
    cartItemList.forEach(item -> {
      orderRequest.setCustomerId(item.getCustomerId());
      orderRequest.setInventoryId(item.getInventoryId());
      orderRequest.setMedName(item.getMedName());
      orderRequest.setQuantity(item.getQuantity());

      // call inventory update api for each order request
      HttpHeaders ivHeaders = new HttpHeaders();
      ivHeaders.set("Authorization", finalToken);
      ivHeaders.set("user", jwtEntity.getUsername());
      ivHeaders.setContentType(MediaType.APPLICATION_JSON);
      HttpEntity<OrderRequest> request = new HttpEntity<>(orderRequest, ivHeaders);
      // TODO : REQUEST DOES NOT NEED A JWT RIGHT NOW AS IT IS NOT CROSSING THROUGH GATEWAY
      ResponseEntity<IVUpdateResponse> response = restTemplate.postForEntity(
          "http://localhost:8081/api/v1/ivm/customer/inventory/{inventoryId}/medicine/batches",
          request,
          IVUpdateResponse.class,
          orderRequest.getInventoryId()
      );
     // TODO Handle req if required quantity greater thn iv quantity

      IVUpdateResponse ivUpdateResponse = response.getBody();
      if(response.getStatusCode() == HttpStatus.OK && ivUpdateResponse.getSuccess()) {
        ivUpdateResponses.add(ivUpdateResponse);
        OrderEntity orderEntity = OrderEntity.builder()
            .orderDate(new Date())
                .customerId(item.getCustomerId())
                    .inventoryId(item.getInventoryId())
                        .quantityOrdered(item.getQuantity())
                            .totalAmount(item.getTotalPrice())
                                .medName(item.getMedName())
                                    .build();


        orderEntities.add(orderEntity);
      } else {
        ivUpdateResponses.add(ivUpdateResponse);
      }
    });


    orderRepository.saveAll(orderEntities);

    OrderSuccessResponse successResponse = new OrderSuccessResponse();

    orderEntities.forEach(order -> {
      successResponse.getItemsOrdered().add(order.getMedName());
      successResponse.getOrderIds().put(order.getOrderId(), order.getMedName());
      successResponse.setOrderDate(order.getOrderDate().toString());
      successResponse.setTotalAmount(successResponse.getTotalAmount() + order.getTotalAmount());
    });

    ivUpdateResponses.forEach(response -> {
      if(!response.getSuccess()) {
        successResponse.getFailedOrders().put(response.getMedName(),
            "Unable to place order. Quantity not available");
      }
    });

    clearCartAfterOrder(orderRequest.getCustomerId(), restTemplate, cartHeaders);

    return successResponse;
  }

  private void clearCartAfterOrder(Integer customerId, RestTemplate restTemplate, HttpHeaders headers) {

    CartClearRequest cartClearRequest = new CartClearRequest();
    cartClearRequest.setCustomerId(customerId);

    HttpEntity<CartClearRequest> requestEntity = new HttpEntity<>(cartClearRequest, headers);

    ResponseEntity<CartClearResponse> response = restTemplate.exchange(
        "http://localhost:8080/api/v1/user/customer-cart/items/",
        HttpMethod.DELETE, requestEntity, CartClearResponse.class);

    log.info("Cart {} has been cleared", response.getBody());
  }

  public GetOrderResponse getAllOrdersByInvID(Integer ivID) {


    List<OrderEntity> orderEntities = orderRepository.findOrderEntitiesByInventoryId(ivID);

    if(orderEntities != null) {
      List<Order> orderList = orderEntities.stream().map(entity -> modelMapper.map(entity,
              com.medsavy.medsavyorderservice.dto.Order.class)).toList();
      return new GetOrderResponse(orderList);
    }

    return new GetOrderResponse();
  }

  public GetOrderResponse getAllOrdersByInvIDAndOrderDate(Integer invId, Date date1, Date date2) {
    List<OrderEntity> orderEntities = orderRepository.findOrderEntitiesByInventoryIdAndOrderDateBetween(invId, date1, date2);

    if(orderEntities != null) {
      List<Order> orderList = orderEntities.stream().map(entity -> modelMapper.map(entity,
          com.medsavy.medsavyorderservice.dto.Order.class)).toList();
      return new GetOrderResponse(orderList);
    }

    return new GetOrderResponse();
  }
}
