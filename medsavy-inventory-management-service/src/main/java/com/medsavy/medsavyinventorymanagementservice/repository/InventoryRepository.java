package com.medsavy.medsavyinventorymanagementservice.repository;

import com.medsavy.medsavyinventorymanagementservice.entity.MedIVEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InventoryRepository extends JpaRepository<MedIVEntity, Integer> {

  @Query(value = "SELECT u FROM MedIVEntity u WHERE u.inventoryId = ?1 AND (u.name LIKE %?2% OR u.type LIKE %?2%)")
  List<MedIVEntity> findMedInventoryEntitiesByInventoryIdAndSearchString(
      Integer inventoryId,
      String searchString);

  @Query(value = "SELECT u FROM MedIVEntity u WHERE (u.name LIKE %?1% OR u.type LIKE %?1%)")
  List<MedIVEntity> findMedInventoryEntitiesBySearchString(String searchString);

  List<MedIVEntity> findMedInventoryEntitiesByInventoryId(Integer IV);

  MedIVEntity findMedInventoryEntityByNameAndAndExpiryDateAndInventoryId(
      String name,
      String expDate,
      Integer inventoryId);

  MedIVEntity findMedInventoryEntityByBatchId(Integer batchId);


}
