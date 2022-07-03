package com.medsavy.medsavyuserservice.Entity;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Subscription")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer subsid;

  private String medName;

  private Integer quantity;

  private Integer customerId;

  private LocalDate date;
}
