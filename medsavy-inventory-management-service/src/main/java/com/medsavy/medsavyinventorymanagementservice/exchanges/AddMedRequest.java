package com.medsavy.medsavyinventorymanagementservice.exchanges;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddMedRequest {

  @NonNull
  private Integer userId;

  @NonNull
  private String name;

  @NonNull
  private String type;

  @NonNull
  private Double price;

  @NonNull
  private Date expiryDate;

  @NonNull
  private Integer quantity;
}
