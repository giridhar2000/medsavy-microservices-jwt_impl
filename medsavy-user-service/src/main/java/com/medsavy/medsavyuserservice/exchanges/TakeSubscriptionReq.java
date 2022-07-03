package com.medsavy.medsavyuserservice.exchanges;

import lombok.Data;

@Data
public class TakeSubscriptionReq {

  private String medName;

  private Integer quantity;

  private Integer customerId;

  private Integer remindAfterMonth;
}
