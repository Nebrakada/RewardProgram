package com.reward.program.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Serialization {

  @Bean
  public ObjectMapper objectMapper() {
    // enable proper parsing of LocalDate to json
    return new ObjectMapper().registerModule(new JavaTimeModule());
  }
}
