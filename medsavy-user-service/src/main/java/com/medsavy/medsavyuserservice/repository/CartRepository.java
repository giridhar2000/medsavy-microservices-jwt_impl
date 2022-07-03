package com.medsavy.medsavyuserservice.repository;

import com.medsavy.medsavyuserservice.Entity.CartItemEntity;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CartRepository extends JpaRepository<CartItemEntity, Integer> {

  List<CartItemEntity> getCartEntitiesByCustomerId(Integer customerId);

  @Modifying
  @Transactional
  @Query(value = "DELETE FROM CartItemEntity c where c.customerId = ?1")
  int deleteCartItemsByCustomerId(Integer customerId);
}
