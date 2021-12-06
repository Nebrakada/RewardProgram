package com.reward.program.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TransactionServiceException extends RuntimeException {
  public TransactionServiceException(String message) {
    super(message);
  }
}
