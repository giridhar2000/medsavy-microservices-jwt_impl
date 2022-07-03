package com.medsavy.medsavyuserservice.repository;

import com.medsavy.medsavyuserservice.Entity.SubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Integer> {

  SubscriptionEntity findByCustomerIdAndMedName(Integer customerId, String medName);
}
