package com.medsavy.medsavyuserservice.exchanges;

import lombok.Data;

@Data
public class UserRegistrationRequest {
  private String username;
  private String password;
  private String roles;
}
