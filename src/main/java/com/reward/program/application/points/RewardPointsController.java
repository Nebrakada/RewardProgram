package com.reward.program.application.points;

import com.reward.program.application.points.dto.RewardPointsResponseDto;
import com.reward.program.application.transaction.Transaction;
import com.reward.program.application.transaction.TransactionService;
import com.reward.program.application.transaction.dto.GetTransactionDto;
import com.reward.program.application.transaction.validator.GetTransactionsValidator;
import com.reward.program.configs.dto.ResponseDto;
import com.reward.program.configs.validator.Validator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.reward.program.configs.dto.ResponseDto.toResponse;

@RestController
@RequestMapping("/api/points")
@RequiredArgsConstructor
public class RewardPointsController {

  private static final Logger logger = LoggerFactory.getLogger(RewardPointsController.class);

  private final TransactionService transactionService;
  private final RewardPointsCalculator rewardPointsCalculator;


  @GetMapping("/{customerId}")
  @ResponseStatus(HttpStatus.OK)
  ResponseDto<RewardPointsResponseDto> getRewardPointsForCustomer(@PathVariable("customerId") Long customerId) {

    logger.debug("fetching transactions for customer with id: " + customerId);
    List<GetTransactionDto> customerTransactions = transactionService.findAllByCustomerId(customerId);
    Validator.validate(new GetTransactionsValidator(), customerTransactions);

    List<Transaction> transactions = customerTransactions.stream()
        .map(GetTransactionDto::toTransaction)
        .collect(Collectors.toList());
    RewardPointsResponseDto rewardPointsResponseDto = rewardPointsCalculator.calculatePoints(transactions, LocalDate.now());

    return toResponse(rewardPointsResponseDto);
  }
}
