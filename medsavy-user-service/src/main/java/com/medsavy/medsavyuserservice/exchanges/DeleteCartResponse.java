package com.medsavy.medsavyuserservice.exchanges;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteCartResponse {

  private Integer deletedRows;
  private Boolean success;
}
