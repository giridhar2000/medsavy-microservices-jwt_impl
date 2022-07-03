package com.medsavy.medsavyinventorymanagementservice.controller;

import com.medsavy.medsavyinventorymanagementservice.exchanges.AddMedRequest;
import com.medsavy.medsavyinventorymanagementservice.exchanges.AddMedResponse;
import com.medsavy.medsavyinventorymanagementservice.exchanges.CreateInventoryRequest;
import com.medsavy.medsavyinventorymanagementservice.exchanges.CreateInventoryResponse;
import com.medsavy.medsavyinventorymanagementservice.exchanges.DeleteMedRequest;
import com.medsavy.medsavyinventorymanagementservice.exchanges.DeleteMedResponse;
import com.medsavy.medsavyinventorymanagementservice.exchanges.GetMedResponse;
import com.medsavy.medsavyinventorymanagementservice.exchanges.GetSalesResponse;
import com.medsavy.medsavyinventorymanagementservice.exchanges.IVUpdateRequest;
import com.medsavy.medsavyinventorymanagementservice.exchanges.IVUpdateResponse;
import com.medsavy.medsavyinventorymanagementservice.services.InventoryManagementService;
import com.medsavy.medsavyinventorymanagementservice.services.InventorySearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ivm")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InventoryManagementController {

  private final InventoryManagementService inventoryManagementService;
  private final InventorySearchService inventorySearchService;

  @PostMapping("/admin/inventory/medicine")
  public AddMedResponse addMedicine(@RequestBody @Validated AddMedRequest request) {
    return inventoryManagementService.addMedInInventory(request);
  }

  @PostMapping("/admin/inventory")
  public CreateInventoryResponse createInventoryForUser(@RequestBody CreateInventoryRequest request) {
    return inventoryManagementService.createInventoryForUser(request.getUserId());
  }

  @GetMapping("/admin/inventory/{inventoryId}/medicine/batches")
  public GetMedResponse getMedicineBatchesForSearchStringAndInventory(@PathVariable
      Integer inventoryId, @RequestParam String searchString) {
    return inventorySearchService.getMedicinesBySearchStringAndIvnID(searchString, inventoryId);
  }

  @GetMapping("/customer/inventory/medicine/batches")
  public GetMedResponse getMedicineBatchesForSearchString(@RequestParam String searchString) {
    return inventorySearchService.getAllMedicinesBySearchString(searchString);
  }

  @GetMapping("/admin/inventory/{inventoryId}/medicines")
  public GetMedResponse getAllMedicinesFromInventory(@PathVariable Integer inventoryId) {
    return inventorySearchService.getAllMedicinesByInventoryId(inventoryId);
  }

  @GetMapping("/customer/inventory/medicines")
  public GetMedResponse getAllMedicines() {
    return inventorySearchService.getAllMedicinesFromInventory();
  }

  @PostMapping("/customer/inventory/{inventoryId}/medicine/batches")
  public IVUpdateResponse updateInventoryAfterSell(@PathVariable Integer inventoryId, @RequestBody
      IVUpdateRequest updateRequest) {

    return inventoryManagementService.updateMedInInventoryAfterSell(inventoryId, updateRequest);
  }

  @GetMapping("/admin/inventory/sales/{salesId}")
  public GetSalesResponse getSalesBySalesID(@PathVariable
      Integer salesId) {
    return inventoryManagementService.getSalesDataBySalesId(salesId);
  }

  @DeleteMapping("/admin/inventory/batch/{batchId}/medicines")
  public DeleteMedResponse removeMedByBatchId(@PathVariable Integer batchId, @RequestBody
      DeleteMedRequest request) {
    return inventoryManagementService.deleteMedByBatchId(batchId, request.getQuantity());
  }

}
