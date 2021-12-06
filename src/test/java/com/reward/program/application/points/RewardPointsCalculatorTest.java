package com.reward.program.application.points;

import com.reward.program.application.customer.Customer;
import com.reward.program.application.points.dto.RewardPointsResponseDto;
import com.reward.program.application.transaction.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RewardPointsCalculatorTest {

  private final RewardPointsCalculator rewardPointsCalculator = new RewardPointsCalculator();

  private LocalDate today;
  private Customer firstCustomer;

  @BeforeEach
  void setUp() {
    today = LocalDate.of(2021, Month.DECEMBER, 5);

    firstCustomer = Customer.builder()
        .id(1L)
        .firstName("John")
        .lastName("Doe")
        .build();
  }

  static Stream<Arguments> singleTransactionDataProvider() {
    return Stream.of(
        Arguments.of(BigDecimal.ZERO, 0),
        Arguments.of(BigDecimal.ONE, 0),
        Arguments.of(new BigDecimal("49"), 0),
        Arguments.of(new BigDecimal("50"), 0),
        Arguments.of(new BigDecimal("51"), 1),
        Arguments.of(new BigDecimal("99"), 49),
        Arguments.of(new BigDecimal("100"), 50),
        Arguments.of(new BigDecimal("101"), 52),
        Arguments.of(new BigDecimal("120"), 90),
        Arguments.of(new BigDecimal("200"), 250)
    );
  }

  @ParameterizedTest
  @MethodSource("singleTransactionDataProvider")
  void shouldCorrectlyCalculatePointsForSingleTransaction(BigDecimal price, Integer expectedAccumulatedPoints) {
    // given
    Customer customer = Customer.builder().id(1L).firstName("John").lastName("Doe").build();
    List<Transaction> transactions = List.of(new Transaction(price, Date.valueOf(today), customer));
    // when
    RewardPointsResponseDto rewardPointsResponse = rewardPointsCalculator.calculatePoints(transactions, today);
    // then
    assertEquals(expectedAccumulatedPoints, rewardPointsResponse.getFirstMonthPoints());
    assertEquals(0, rewardPointsResponse.getSecondMonthPoints());
    assertEquals(0, rewardPointsResponse.getThirdMonthPoints());
    assertEquals(expectedAccumulatedPoints, rewardPointsResponse.getTotalPoints());
  }

  @Test
  void shouldCorrectlyCalculatePointsForManyTransactions() {
    // given
    List<Transaction> transactions = getFirstCustomerTransactions();
    // when
    RewardPointsResponseDto rewardPointsResponse = rewardPointsCalculator.calculatePoints(transactions, today);
    // then
    assertEquals(95, rewardPointsResponse.getFirstMonthPoints());
    assertEquals("5. November 2021", rewardPointsResponse.getFirstMonthName());
    assertEquals(250, rewardPointsResponse.getSecondMonthPoints());
    assertEquals("5. October 2021", rewardPointsResponse.getSecondMonthName());
    assertEquals(0, rewardPointsResponse.getThirdMonthPoints());
    assertEquals("5. September 2021", rewardPointsResponse.getThirdMonthName());
    assertEquals(345, rewardPointsResponse.getTotalPoints());
  }



  private List<Transaction> getFirstCustomerTransactions() {
    Transaction t1 = Transaction.builder()
        .id(1L)
        .price(new BigDecimal("120"))
        .date(Date.valueOf(LocalDate.of(2021, 12, 1)))
        .customer(firstCustomer)
        .build();
    Transaction t2 = Transaction.builder()
        .id(1L)
        .price(new BigDecimal("55"))
        .date(Date.valueOf(LocalDate.of(2021, 11, 14)))
        .customer(firstCustomer)
        .build();
    Transaction t3 = Transaction.builder()
        .id(1L)
        .price(new BigDecimal("200"))
        .date(Date.valueOf(LocalDate.of(2021, 11, 3)))
        .customer(firstCustomer)
        .build();
    Transaction t4 = Transaction.builder()
        .id(1L)
        .price(new BigDecimal("40"))
        .date(Date.valueOf(LocalDate.of(2021, 12, 14)))
        .customer(firstCustomer)
        .build();
    Transaction t5 = Transaction.builder()
        .id(1L)
        .price(new BigDecimal("140"))
        .date(Date.valueOf(LocalDate.of(2021, 5, 14)))
        .customer(firstCustomer)
        .build();
    return List.of(t1, t2, t3, t4);
  }
}

