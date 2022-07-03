package com.medsavy.medsavyorderservice.exchanges;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IVUpdateResponse {

  private String medName;

  private String message;

  private Boolean success;
}
