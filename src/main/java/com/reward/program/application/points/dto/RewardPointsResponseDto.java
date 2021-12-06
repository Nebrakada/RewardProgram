package com.reward.program.application.points.dto;

import com.reward.program.application.customer.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RewardPointsResponseDto {
  private Customer customer;
  private String firstMonthName;
  private Integer firstMonthPoints;
  private String secondMonthName;
  private Integer secondMonthPoints;
  private String thirdMonthName;
  private Integer thirdMonthPoints;

  private Integer totalPoints;
}
