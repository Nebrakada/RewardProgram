package com.reward.program.application.exception;

import com.reward.program.configs.dto.ResponseDto;
import com.reward.program.configs.validator.ValidatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.reward.program.configs.dto.ResponseDto.toErrorResponse;

@RestControllerAdvice
public class AppExceptionHandler {
  private static final Logger logger = LoggerFactory.getLogger(AppExceptionHandler.class);

  @SuppressWarnings("rawtypes")
  @ExceptionHandler(ValidatorException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseDto handleValidatorException(ValidatorException exception) {
    logger.error("Validator exception: " + exception.getMessage());
    return toErrorResponse(exception);
  }


  @SuppressWarnings("rawtypes")
  @ExceptionHandler(TransactionServiceException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseDto handleValidatorException(TransactionServiceException exception) {
    logger.error("Transaction service thrown exception: " + exception.getMessage());
    return toErrorResponse(exception);
  }

  @SuppressWarnings("rawtypes")
  @ExceptionHandler({RuntimeException.class, Exception.class})
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseDto handleException(RuntimeException exception) {
    logger.error("Unknown exception: " + exception.getMessage());
    return toErrorResponse(exception);
  }
}
