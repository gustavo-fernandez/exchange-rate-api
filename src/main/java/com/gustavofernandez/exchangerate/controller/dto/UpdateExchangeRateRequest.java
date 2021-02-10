package com.gustavofernandez.exchangerate.controller.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class UpdateExchangeRateRequest {

  @NotNull
  @Length(min = 3, max = 3)
  private String sourceCurrency;
  @NotNull
  @Length(min = 3, max = 3)
  private String targetCurrency;
  @NotNull
  @Min(0)
  private BigDecimal rate;

}
