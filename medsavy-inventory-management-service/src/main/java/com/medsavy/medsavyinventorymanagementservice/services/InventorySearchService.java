package com.medsavy.medsavyinventorymanagementservice.services;

import com.medsavy.medsavyinventorymanagementservice.exchanges.GetMedResponse;

public interface InventorySearchService {

  /**
   * This method returns a list of medicines that matches the search string in a inventory.
   * Method is used by admins.
   * @param searchString either name or type of the med.
   * @param inventoryId inventory where the search should happen.

   */
  GetMedResponse getMedicinesBySearchStringAndIvnID(String searchString, Integer inventoryId);

  /**
   * Gets all medicines by search string irrespective of inventory.
   * Method is used by customers
   * @param searchString
   * @return
   */
  GetMedResponse getAllMedicinesBySearchString(String searchString);

  /**
   * Returns all meds present in an inventoru.
   * @param inventoryId
   */
  GetMedResponse getAllMedicinesByInventoryId(Integer inventoryId);

  GetMedResponse getAllMedicinesFromInventory();

}
