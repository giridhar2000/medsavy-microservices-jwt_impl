package com.medsavy.medsavyuserservice.repository;

import com.medsavy.medsavyuserservice.Entity.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

  Boolean existsByUsername(String username);

  Optional<UserEntity> findByUsername(String username);
}
