package com.gustavofernandez.exchangerate.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExchangeRateResponse {

  private BigDecimal amount;
  private BigDecimal rate;
  private Request request;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Request {
    private BigDecimal amount;
    private String sourceCurrency;
    private String targetCurrency;
  }
}