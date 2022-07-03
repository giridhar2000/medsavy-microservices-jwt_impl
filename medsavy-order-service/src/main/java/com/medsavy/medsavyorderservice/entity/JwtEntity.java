package com.medsavy.medsavyorderservice.entity;

import com.netflix.discovery.converters.Auto;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "UserTokens")
@Data
public class JwtEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  private Integer userId;
  private String token;
  private String username;
}
