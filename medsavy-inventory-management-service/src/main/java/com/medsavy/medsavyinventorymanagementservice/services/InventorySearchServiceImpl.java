package com.medsavy.medsavyinventorymanagementservice.services;

import com.medsavy.medsavyinventorymanagementservice.dto.Batch;
import com.medsavy.medsavyinventorymanagementservice.dto.Medicine;
import com.medsavy.medsavyinventorymanagementservice.entity.MedIVEntity;
import com.medsavy.medsavyinventorymanagementservice.exchanges.GetMedResponse;
import com.medsavy.medsavyinventorymanagementservice.repository.InventoryRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InventorySearchServiceImpl implements InventorySearchService{

  private final InventoryRepository inventoryRepository;

  @Override
  public GetMedResponse getMedicinesBySearchStringAndIvnID(String searchString, Integer inventoryId) {
    List<MedIVEntity> medIVEntities = inventoryRepository
        .findMedInventoryEntitiesByInventoryIdAndSearchString(inventoryId, searchString);

    List<Medicine> medicines = structureMedicineList(medIVEntities);

    return new GetMedResponse(medicines);
  }

  @Override
  public GetMedResponse getAllMedicinesBySearchString(String searchString) {
    List<MedIVEntity> medIVEntities = inventoryRepository
        .findMedInventoryEntitiesBySearchString(searchString);

    List<Medicine> medicines = structureMedicineList(medIVEntities);

    return new GetMedResponse(medicines);
  }

  @Override
  public GetMedResponse getAllMedicinesByInventoryId(Integer inventoryId) {
    List<MedIVEntity> medIVEntities = inventoryRepository
        .findMedInventoryEntitiesByInventoryId(inventoryId);

    List<Medicine> medicineList = structureMedicineList(medIVEntities);

    return new GetMedResponse(medicineList);
  }

  @Override
  public GetMedResponse getAllMedicinesFromInventory() {
    List<Medicine> medicineList = new ArrayList<>();
    List<MedIVEntity> medIVEntities = inventoryRepository.findAll();

    medicineList = structureMedicineList(medIVEntities);
    return new GetMedResponse(medicineList);
  }

  /**
   * Method structures medicines, like each med will have list of batches
   * @param medIVEntities
   * @return
   */
  private List<Medicine> structureMedicineList(List<MedIVEntity> medIVEntities) {
    List<Medicine> medicineList = new ArrayList<>();
    Map<String, Medicine> medicineBatch = new HashMap<>();

    medIVEntities.forEach(medIVEntity -> {
      Batch batch = new Batch(medIVEntity.getBatchId(), medIVEntity.getInventoryId(),
          medIVEntity.getExpiryDate(), medIVEntity.getQuantity());
      Medicine medicine = null;
      if(!medicineBatch.containsKey(medIVEntity.getName())) {
        medicine = new Medicine(
            medIVEntity.getName(),
            medIVEntity.getType(),
            medIVEntity.getPrice()
        );

        //medicine.addBatch(batch);

        //medicine.setTotalQty(batch.getQuantity());
        medicineBatch.put(medicine.getName(), medicine);
        medicineList.add(medicine);
      } else {
        medicine = medicineBatch.get(medIVEntity.getName());
       // medicine.addBatch(batch);
        //medicine.setTotalQty(medicine.getTotalQty() + batch.getQuantity());
      }

      medicine.addBatchToIVMap(medIVEntity.getInventoryId(), batch);
    });
    return medicineList;
  }

//  private void structureMedicineListForCustomer(List<MedIVEntity> medIVEntities) {
//    List<Medicine> medicineList = new ArrayList<>();
//    List<Seller> sellerList = new ArrayList<>();
//    Map<String, Medicine> medicineBatch = new HashMap<>();
//    Map<String, List<Seller>> medicineSellerMap = new HashMap<>();
//
//    medIVEntities.forEach(medIVEntity -> {
//
//      if(!medicineSellerMap.containsKey(medIVEntity.getName())) {
//        medicineSellerMap.put(medIVEntity.getName(), List.of(new Seller(medIVEntity.getInventoryId(), )) )
//      }
//    });
//
//  }
}
