package com.reward.program.application.transaction.validator;

import com.reward.program.application.transaction.dto.UpdateTransactionDto;
import com.reward.program.configs.validator.Validator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;

public class UpdateTransactionValidator implements Validator<UpdateTransactionDto> {

  private final Map<String, String> errors = new HashMap<>();

  public Map<String, String> validate(UpdateTransactionDto transaction) {
    if (transaction.getId() == null || transaction.getId() < 0) {
      errors.put("id", "id is null or negative value");
    }

    if (hasInvalidPrice(transaction)) {
      errors.put("price", "price has incorrect value - lower than 0");
    }

    if (isDateInvalid(transaction.getDate())) {
      errors.put("date", "date is in the future");
    }

    if (isNull(transaction.getCustomer())) {
      errors.put("customer", "customer is null");
    }
    return errors;
  }

  private boolean isDateInvalid(LocalDate date) {
    return date.isAfter(LocalDate.now());
  }

  private boolean hasInvalidPrice(UpdateTransactionDto transaction) {
    return transaction.getPrice().compareTo(BigDecimal.ZERO) < 0;
  }
}
