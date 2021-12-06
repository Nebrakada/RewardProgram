package com.reward.program;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class RewardProgramApplication {

  public static void main(String[] args) {
    SpringApplication.run(RewardProgramApplication.class, args);
  }
}
