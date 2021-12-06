package com.reward.program.application.transaction;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reward.program.application.customer.Customer;
import com.reward.program.application.transaction.dto.CreateTransactionDto;
import com.reward.program.application.transaction.dto.CreateTransactionResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

  @Autowired
  public MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private TransactionService transactionService;

  @Test
  void addTransactionTest() throws Exception {
    // given

    BigDecimal transactionPrice = new BigDecimal("15");
    LocalDate transactionDate = LocalDate.of(2021, 5, 15);

    Customer customer = Customer.builder().id(1L).firstName("John").lastName("Doe").build();
    CreateTransactionDto createTransactionDto = CreateTransactionDto.builder()
        .price(transactionPrice)
        .date(transactionDate)
        .customerId(customer.getId())
        .build();
    CreateTransactionResponseDto createTransactionResponseDto = CreateTransactionResponseDto.builder()
        .id(1L)
        .price(transactionPrice)
        .date(transactionDate)
        .customer(customer)
        .build();
    when(transactionService
        .addTransaction(createTransactionDto))
        .thenReturn(createTransactionResponseDto);

    String createTransactionJson = objectMapper.writeValueAsString(createTransactionDto);

    // expect
    mockMvc.perform(MockMvcRequestBuilders
            .post("/api/transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(createTransactionJson))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.data.id").exists())
        .andExpect(jsonPath("$.data.id").value(1L))
        .andExpect(jsonPath("$.data.price").value(transactionPrice))
        .andExpect(jsonPath("$.data.date").value("2021-05-15"))
        .andExpect(jsonPath("$.data.customer.firstName").value("John"));
  }
}
