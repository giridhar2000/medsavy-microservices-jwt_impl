package com.medsavy.medsavyinventorymanagementservice.repository;

import com.medsavy.medsavyinventorymanagementservice.entity.UserEntity;
import com.medsavy.medsavyinventorymanagementservice.entity.UserIVEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserInventoryRepository extends JpaRepository<UserIVEntity, Integer> {

  @Query(value = "SELECT u FROM UserIVEntity u WHERE u.userEntity.id = ?1")
  UserIVEntity findUserInventoryEntityByUserId(Integer id);

  Boolean existsByUserEntity(UserEntity userEntity);
}
