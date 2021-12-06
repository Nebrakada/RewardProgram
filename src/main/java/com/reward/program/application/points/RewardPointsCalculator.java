package com.reward.program.application.points;

import com.reward.program.application.points.dto.RewardPointsResponseDto;
import com.reward.program.application.transaction.Transaction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class RewardPointsCalculator {

  private static final BigDecimal FIFTY_DOLLARS = new BigDecimal("50");
  private static final BigDecimal HUNDRED_DOLLARS = new BigDecimal("100");
  private static final BigDecimal OVER_HUNDRED_POINTS_MULTIPLIER = new BigDecimal("2");
  private static final DateTimeFormatter monthYearFormatter = DateTimeFormatter.ofPattern("d. MMMM yyyy");

  public RewardPointsResponseDto calculatePoints(List<Transaction> transactions, LocalDate dateToCountPointsFrom) {
    LocalDate oneMonthAgo = dateToCountPointsFrom.minusMonths(1);
    LocalDate twoMonthsAgo = dateToCountPointsFrom.minusMonths(2);
    LocalDate threeMonthsAgo = dateToCountPointsFrom.minusMonths(3);

    Integer oneMonthAgoPoints = calculate(filterTransactions(transactions, oneMonthAgo, dateToCountPointsFrom));
    Integer twoMonthsAgoPoints = calculate(filterTransactions(transactions, twoMonthsAgo, oneMonthAgo));
    Integer threeMonthsAgoPoints = calculate(filterTransactions(transactions, threeMonthsAgo, twoMonthsAgo));
    int totalPointsForLastThreeMonths = oneMonthAgoPoints + twoMonthsAgoPoints + threeMonthsAgoPoints;

    return RewardPointsResponseDto.builder()
        .customer(transactions.get(0).getCustomer())
        .firstMonthName(oneMonthAgo.format(monthYearFormatter))
        .firstMonthPoints(oneMonthAgoPoints)
        .secondMonthName(twoMonthsAgo.format(monthYearFormatter))
        .secondMonthPoints(twoMonthsAgoPoints)
        .thirdMonthName(threeMonthsAgo.format(monthYearFormatter))
        .thirdMonthPoints(threeMonthsAgoPoints)
        .totalPoints(totalPointsForLastThreeMonths)
        .build();
  }

  private Function<BigDecimal, Integer> calculatePoints = (price) -> {
    int totalPoints;
    if (isLessThan(FIFTY_DOLLARS, price)) {
      totalPoints = 0;
    } else if (isLessThan(HUNDRED_DOLLARS, price)) {
      totalPoints = price.intValue() - 50;
    } else {
      totalPoints = 50 + price.subtract(HUNDRED_DOLLARS).multiply(OVER_HUNDRED_POINTS_MULTIPLIER).intValue();
    }
    return totalPoints;
  };

  private Integer calculate(List<Transaction> transactions) {
    return transactions.stream()
        .map(Transaction::getPrice)
        .map(calculatePoints)
        .mapToInt(Integer::intValue)
        .sum();
  }

  public List<Transaction> filterTransactions(List<Transaction> transactions, LocalDate from, LocalDate to) {
    return transactions.stream()
        .filter(filterTransactionsBetweenDatesPredicate(from, to))
        .collect(Collectors.toList());
  }

  private Predicate<Transaction> filterTransactionsBetweenDatesPredicate(LocalDate from, LocalDate to) {
    return transaction -> transaction.getDate().toLocalDate().isBefore(to.plusDays(1))
        && transaction.getDate().toLocalDate().isAfter(from.minusDays(1));
  }

  private boolean isLessThan(BigDecimal lessThanThreshold, BigDecimal price) {
    return price.compareTo(lessThanThreshold) < 0;
  }
}
