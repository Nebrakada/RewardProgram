package com.reward.program;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reward.program.application.customer.Customer;
import com.reward.program.application.transaction.dto.CreateTransactionDto;
import com.reward.program.application.transaction.dto.UpdateTransactionDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RewardProgramApplicationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void shouldCalculateRewardPointsForCustomerCorrectly() throws Exception {
    // given
    String customerId = "1";
    // expect
    mockMvc.perform(get("/api/points/" + customerId)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.customer.firstName").value("John"))
        .andExpect(jsonPath("$.data.firstMonthName").value("6. November 2021"))
        .andExpect(jsonPath("$.data.firstMonthPoints").value(95))
        .andExpect(jsonPath("$.data.secondMonthName").value("6. October 2021"))
        .andExpect(jsonPath("$.data.secondMonthPoints").value(236))
        .andExpect(jsonPath("$.data.thirdMonthName").value("6. September 2021"))
        .andExpect(jsonPath("$.data.thirdMonthPoints").value(0))
        .andExpect(jsonPath("$.data.totalPoints").value(331));
  }

  @Test
  public void shouldReturnErrorWhenTryingToCalculatePointsForCustomerWithoutTransactions() throws Exception {
    // given
    String customerId = "15";
    // expect
    String expectedValidationError = "[VALIDATION ERRORS]: transactions: transaction list is empty";

    mockMvc.perform(get("/api/points/" + customerId)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(response -> assertEquals(expectedValidationError, response.getResolvedException().getMessage()));
  }

  @Test
  public void shouldReturnBadRequestOnInvalidEntryData() throws Exception {
    BigDecimal transactionPrice = new BigDecimal("-5");
    LocalDate transactionDate = LocalDate.of(2021, 5, 15);

    Customer customer = Customer.builder().id(1L).firstName("John").lastName("Doe").build();
    CreateTransactionDto createTransactionDto = CreateTransactionDto.builder()
        .price(transactionPrice)
        .date(transactionDate)
        .customerId(customer.getId())
        .build();

    String createTransactionJson = objectMapper.writeValueAsString(createTransactionDto);

    // expect
    String expectedValidationError = "[VALIDATION ERRORS]: price: price can't be lower than 0";

    mockMvc.perform(post("/api/transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(createTransactionJson))
        .andExpect(status().isBadRequest())
        .andExpect(response -> assertEquals(expectedValidationError, response.getResolvedException().getMessage()));
  }


  @Test
  public void shouldReturnNotFoundCodeWhenUpdatingTransactionOnNonExistingTransactionId() throws Exception {
    // given
    long nonExistentTransactionId = 123L;
    Customer customer = Customer.builder().id(1L).firstName("John").lastName("Doe").build();
    UpdateTransactionDto updateTransactionDto = UpdateTransactionDto.builder()
        .id(1L)
        .price(new BigDecimal("250"))
        .date(LocalDate.of(2021, 12, 6))
        .customer(customer)
        .build();
    String updateTransactionJson = objectMapper.writeValueAsString(updateTransactionDto);

    // expect
    mockMvc.perform(put("/api/transactions/" + nonExistentTransactionId)
            .content(updateTransactionJson)
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isNotFound());
  }
}
