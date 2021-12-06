package com.reward.program.application.customer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.reward.program.application.transaction.Transaction;
import com.reward.program.configs.entity.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@ToString(exclude = "transactions")
@Entity
@Table(name = "customers")
public class Customer extends BaseEntity {
  private Long id;
  @Column(name = "FIRST_NAME")
  private String firstName;
  @Column(name = "LAST_NAME")
  private String lastName;

  @OneToMany(mappedBy = "customer")
  @Builder.Default
  @JsonIgnore
  private List<Transaction> transactions = new ArrayList<>();


}
