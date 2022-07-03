package com.medsavy.medsavyuserservice.exchanges;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class UserRegistrationResponse {
  private String username;
  private String roles;
  private String message;
  private Integer inventoryId;
}
