package com.gustavofernandez.exchangerate.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExchangeRateRequest {
  private BigDecimal amount;
  private String sourceCurrency;
  private String targetCurrency;
}
