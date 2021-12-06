package com.reward.program.application.transaction.dto;

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
public class CreateTransactionDto {

  private BigDecimal price;
  private LocalDate date;
  private Long customerId;

  public Transaction toTransaction() {
    return Transaction.builder()
        .price(price)
        .date(Date.valueOf(date))
        .build();
  }
}
