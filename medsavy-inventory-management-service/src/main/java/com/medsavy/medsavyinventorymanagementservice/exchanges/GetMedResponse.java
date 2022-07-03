package com.medsavy.medsavyinventorymanagementservice.exchanges;

import com.medsavy.medsavyinventorymanagementservice.dto.Medicine;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetMedResponse {

  private List<Medicine> medicineList = new ArrayList<>();
}
