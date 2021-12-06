package com.reward.program.application.transaction;

import com.reward.program.application.customer.Customer;
import com.reward.program.application.customer.CustomerRepository;
import com.reward.program.application.exception.TransactionServiceException;
import com.reward.program.application.transaction.dto.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionService {

  private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);
  private final TransactionRepository transactionRepository;
  private final CustomerRepository customerRepository;

  @Transactional(isolation = Isolation.SERIALIZABLE)
  public CreateTransactionResponseDto addTransaction(CreateTransactionDto createTransactionDto) {
    Optional<Customer> maybeCustomer = customerRepository.findById(createTransactionDto.getCustomerId());
    logger.debug("Adding transaction to db: " + createTransactionDto);
    if (maybeCustomer.isEmpty()) {
      throw new TransactionServiceException("Customer with id " + createTransactionDto.getCustomerId() + " does not exist");
    }
    Customer customer = maybeCustomer.get();
    Transaction transactionToSave = createTransactionDto.toTransaction().withCustomer(customer);
    Transaction savedTransaction = transactionRepository.save(transactionToSave);
    logger.info("Added transaction to db: " + savedTransaction.toCreateTransactionResponseDto());
    return CreateTransactionResponseDto.builder()
        .id(savedTransaction.getId())
        .price(savedTransaction.getPrice())
        .date(savedTransaction.getDate().toLocalDate())
        .build();
  }

  public List<GetTransactionDto> findAllByCustomerId(Long customerId) {
    // security validation - check if user is authorized to access this data
    logger.debug("Fetching transactions for customer with id: " + customerId);
    List<Transaction> customerTransactions = transactionRepository.findByCustomerId(customerId);
    logger.info("fetched transactions: " + customerTransactions);
    return customerTransactions
        .stream()
        .map(Transaction::toGetTransactionDto)
        .collect(Collectors.toList());
  }

  public UpdateTransactionResponseDto updateTransaction(Long transactionId, UpdateTransactionDto updateTransactionDto) {
    logger.debug("Updating transaction with id: " + transactionId + ", with body: " + updateTransactionDto);
    return transactionRepository.findById(transactionId)
        .map(tr -> tr.toUpdateTransactionResponseDto(updateTransactionDto))
        .orElseThrow(() -> new TransactionServiceException("Error updating transaction. No transaction with id " + transactionId));
  }
}
