package com.medsavy.medsavyuserservice.exchanges;

import lombok.Data;

@Data
public class UserLoginRequest {
  private String username;
  private String password;
}
