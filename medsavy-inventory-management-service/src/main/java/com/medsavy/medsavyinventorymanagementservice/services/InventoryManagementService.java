package com.medsavy.medsavyinventorymanagementservice.services;

import com.medsavy.medsavyinventorymanagementservice.exchanges.AddMedRequest;
import com.medsavy.medsavyinventorymanagementservice.exchanges.AddMedResponse;
import com.medsavy.medsavyinventorymanagementservice.exchanges.CreateInventoryResponse;
import com.medsavy.medsavyinventorymanagementservice.exchanges.DeleteMedResponse;
import com.medsavy.medsavyinventorymanagementservice.exchanges.GetMedResponse;
import com.medsavy.medsavyinventorymanagementservice.exchanges.GetSalesResponse;
import com.medsavy.medsavyinventorymanagementservice.exchanges.IVUpdateRequest;
import com.medsavy.medsavyinventorymanagementservice.exchanges.IVUpdateResponse;

public interface InventoryManagementService {

  /**
   * This method adds a medicine in inventory as batches .
   * A medicine can have multiple batches. Each batch has different expiry date for that med.
   * While adding a medicine batch , if the med exists and has similar expiry date then no new
   * batch is created, the previous gets updated with the quantity in this case.
   * @param request contains fields such as userId - User who is adding the medicine
   *                name - medicine name
   *                type - type of the medicine, price , expiryDate, quantity
   * @return returns AddMedResponse object containing the batchId of that newly added med.
   * name of the med, inventoryId where it got added.
   */
  AddMedResponse addMedInInventory(AddMedRequest request);

  /**
   * This method created a new inventory for a registered user. It does not allow more than
   * one inventory for a user.
   * @param userId id of the user for whom inventory will be created.
   */
  CreateInventoryResponse createInventoryForUser(Integer userId);


  /**
   * This medthod updates an inventory when a customer places an order for meds.
   * The update may happen in multiple batches for a med based on the quantity required by customer.
   * Ordered gets marked successful only when inventory is updated successfully.
   * Method also generates a sales record after successfully updating inventory.
   * @param inventoryId Inventory id where update should happen.
   * @param updateRequest Has fields like customer who placed the order , inventory to be updated .
   * @return
   */
  IVUpdateResponse updateMedInInventoryAfterSell(Integer inventoryId, IVUpdateRequest updateRequest);

  GetSalesResponse getSalesDataBySalesId(Integer salesId);

  DeleteMedResponse deleteMedByBatchId(Integer batchId, Integer quantity);
}
