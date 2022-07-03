package com.medsavy.medsavyorderservice.repository;

import com.medsavy.medsavyorderservice.entity.JwtEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<JwtEntity, Integer> {

  JwtEntity findJwtEntityByUserId(Integer id);
}
