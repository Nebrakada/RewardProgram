package com.reward.program.application.transaction;

import com.reward.program.application.transaction.dto.*;
import com.reward.program.application.transaction.validator.CreateTransactionValidator;
import com.reward.program.application.transaction.validator.UpdateTransactionValidator;
import com.reward.program.configs.dto.ResponseDto;
import com.reward.program.configs.validator.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

  private final TransactionService transactionService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  ResponseDto<CreateTransactionResponseDto> addTransaction(@RequestBody CreateTransactionDto createTransactionDto) {
    // validate entry data
    Validator.validate(new CreateTransactionValidator(), createTransactionDto);
    // save transaction and send response
    CreateTransactionResponseDto createdTransaction = transactionService.addTransaction(createTransactionDto);
    return ResponseDto.toResponse(createdTransaction);
  }

  @GetMapping("/{customerId}")
  @ResponseStatus(HttpStatus.OK)
  ResponseDto<List<GetTransactionDto>> getAllTransactionsForCustomer(@PathVariable("customerId") Long customerId) {
    // in security context verify that user fetching transactions is authorized to do so
    List<GetTransactionDto> allTransactionsForCustomer = transactionService.findAllByCustomerId(customerId);
    return ResponseDto.toResponse(allTransactionsForCustomer);
  }

  @PutMapping("/{transactionId}")
  @ResponseStatus(HttpStatus.OK)
  ResponseDto<UpdateTransactionResponseDto> updateTransaction(@PathVariable("transactionId") Long id,
                                                              @RequestBody UpdateTransactionDto updateTransactionDto) {
    // validate entry data
    Validator.validate(new UpdateTransactionValidator(), updateTransactionDto);
    // security validation - check if user sending request is authorized to update transaction
    UpdateTransactionResponseDto updatedTransactionDto = transactionService.updateTransaction(id, updateTransactionDto);
    return ResponseDto.toResponse(updatedTransactionDto);
  }
}
