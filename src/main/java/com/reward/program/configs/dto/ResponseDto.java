package com.reward.program.configs.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseDto<T> {
  private T data;
  private String error;

  public static <T> ResponseDto<T> toResponse(T t) {
    return ResponseDto.<T>builder()
        .data(t)
        .build();
  }

  @SuppressWarnings("rawtypes")
  public static ResponseDto toErrorResponse(RuntimeException ex) {
    return ResponseDto.builder()
        .error(ex.getMessage())
        .build();
  }
}
