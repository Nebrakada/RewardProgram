package com.reward.program.application.transaction;

import com.reward.program.application.customer.Customer;
import com.reward.program.application.transaction.dto.CreateTransactionResponseDto;
import com.reward.program.application.transaction.dto.GetTransactionDto;
import com.reward.program.application.transaction.dto.UpdateTransactionDto;
import com.reward.program.application.transaction.dto.UpdateTransactionResponseDto;
import com.reward.program.configs.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@ToString
@Entity
@Table(name = "transactions")
public class Transaction extends BaseEntity {

  private BigDecimal price;

  private Date date;

  @ManyToOne(cascade = {CascadeType.ALL})
  @JoinColumn(name = "customer_id")
  private Customer customer;

  public Transaction withCustomer(Customer customer) {
    this.customer = customer;
    return this;
  }

  public CreateTransactionResponseDto toCreateTransactionResponseDto() {
    return CreateTransactionResponseDto.builder()
        .id(id)
        .price(price)
        .date(new Date(date.getTime()).toLocalDate())
        .customer(customer)
        .build();
  }

  public GetTransactionDto toGetTransactionDto() {
    return GetTransactionDto.builder()
        .id(id)
        .price(price)
        .date(date.toLocalDate())
        .customer(customer)
        .build();
  }

  public UpdateTransactionResponseDto toUpdateTransactionResponseDto(UpdateTransactionDto updateTransactionDto) {
    return UpdateTransactionResponseDto.builder()
        .id(updateTransactionDto.getId())
        .price(updateTransactionDto.getPrice())
        .date(updateTransactionDto.getDate())
        .customer(updateTransactionDto.getCustomer())
        .build();
  }
}
