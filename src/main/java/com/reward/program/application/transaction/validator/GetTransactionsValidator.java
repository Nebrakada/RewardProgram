package com.reward.program.application.transaction.validator;

import com.reward.program.application.transaction.dto.GetTransactionDto;
import com.reward.program.configs.validator.Validator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetTransactionsValidator implements Validator<List<GetTransactionDto>> {

  private final Map<String, String> errors = new HashMap<>();

  public Map<String, String> validate(List<GetTransactionDto> transactions) {
    if (transactions.isEmpty()) {
      errors.put("transactions", "transaction list is empty");
    }
    return errors;
  }
}
