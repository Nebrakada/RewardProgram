package com.reward.program.application.transaction.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.reward.program.application.customer.Customer;
import com.reward.program.application.transaction.Transaction;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class GetTransactionDto {
  private Long id;
  private BigDecimal price;
  private LocalDate date;
  private Customer customer;

  public Transaction toTransaction() {
    return Transaction.builder()
        .id(id)
        .price(price)
        .date(Date.valueOf(date))
        .customer(customer)
        .build();
  }
}
