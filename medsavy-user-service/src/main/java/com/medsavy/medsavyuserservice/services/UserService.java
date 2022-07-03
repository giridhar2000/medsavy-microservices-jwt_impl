package com.medsavy.medsavyuserservice.services;

import com.medsavy.medsavyuserservice.exchanges.AddCartRequest;
import com.medsavy.medsavyuserservice.exchanges.AddCartResponse;
import com.medsavy.medsavyuserservice.exchanges.DeleteCartResponse;
import com.medsavy.medsavyuserservice.exchanges.GetCartResponse;
import com.medsavy.medsavyuserservice.exchanges.TakeSubscriptionReq;
import com.medsavy.medsavyuserservice.exchanges.TakeSubscriptionResponse;

public interface UserService {

  public AddCartResponse addMedIntoCart(AddCartRequest request);

  public GetCartResponse getCartByCustomerId(Integer customerId);

  public DeleteCartResponse deleteCartItemsByCustomerId(Integer customerId);

  public TakeSubscriptionResponse takeSubscription(TakeSubscriptionReq request);
}
