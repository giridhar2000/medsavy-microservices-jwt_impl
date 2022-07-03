package com.medsavy.medsavyuserservice.repository;

import com.medsavy.medsavyuserservice.Entity.JwtEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<JwtEntity, Integer> {

  JwtEntity findJwtEntityByUserId(Integer id);
}
