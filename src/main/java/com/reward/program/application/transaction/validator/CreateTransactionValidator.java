package com.reward.program.application.transaction.validator;

import com.reward.program.application.transaction.dto.CreateTransactionDto;
import com.reward.program.configs.validator.Validator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;

public class CreateTransactionValidator implements Validator<CreateTransactionDto> {

  private final Map<String, String> errors = new HashMap<>();

  public Map<String, String> validate(CreateTransactionDto transaction) {
    if (isPriceLowerThanZero(transaction)) {
      errors.put("price", "price can't be lower than 0");
    }

    if (isDateValid(transaction.getDate())) {
      errors.put("date", "date can't be from the future");
    }

    if (isNull(transaction.getCustomerId()) || transaction.getCustomerId() < 0) {
      errors.put("customerId", "customerId is empty or invalid");
    }
    return errors;
  }

  private boolean isDateValid(LocalDate date) {
    return date.isAfter(LocalDate.now());
  }

  private boolean isPriceLowerThanZero(CreateTransactionDto transaction) {
    return transaction.getPrice().compareTo(BigDecimal.ZERO) < 0;
  }
}
