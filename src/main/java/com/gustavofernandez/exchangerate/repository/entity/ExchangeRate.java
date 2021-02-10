package com.gustavofernandez.exchangerate.repository.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "exchange_rate")
@Entity
@ToString
@Getter
@Setter
public class ExchangeRate {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String sourceCurrency;
  private String targetCurrency;
  private BigDecimal rate;

}
