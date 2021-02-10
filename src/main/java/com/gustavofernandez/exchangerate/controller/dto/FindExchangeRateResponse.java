package com.gustavofernandez.exchangerate.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FindExchangeRateResponse {

  private Long id;
  private BigDecimal rate;
  private String sourceCurrency;
  private String targetCurrency;

}
