package com.medsavy.medsavyorderservice.repository;

import com.medsavy.medsavyorderservice.entity.OrderEntity;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {

  List<OrderEntity> findOrderEntitiesByInventoryId(Integer inventoryId);

  List<OrderEntity> findOrderEntitiesByInventoryIdAndOrderDateBetween(Integer id, Date st, Date ed);
}
