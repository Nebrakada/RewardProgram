package com.reward.program.application.transaction.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.reward.program.application.customer.Customer;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UpdateTransactionResponseDto {
  private Long id;
  private BigDecimal price;
  private LocalDate date;
  private Customer customer;
}
