package com.gustavofernandez.exchangerate.service;

import com.gustavofernandez.exchangerate.controller.dto.ExchangeRateRequest;
import com.gustavofernandez.exchangerate.controller.dto.ExchangeRateResponse;
import com.gustavofernandez.exchangerate.controller.dto.FindExchangeRateResponse;
import com.gustavofernandez.exchangerate.controller.dto.UpdateExchangeRateRequest;
import com.gustavofernandez.exchangerate.mapper.ExchangeRateMapper;
import com.gustavofernandez.exchangerate.repository.ExchangeRateRepository;
import io.reactivex.Observable;
import io.reactivex.Single;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Repository
@RequiredArgsConstructor
public class ExchangeRateService {

  private final ExchangeRateRepository exchangeRateRepository;
  private final ExchangeRateMapper exchangeRateMapper;

  public Single<ExchangeRateResponse> find(ExchangeRateRequest exchangeRateRequest) {
    return exchangeRateRepository
        .find(exchangeRateRequest.getSourceCurrency(), exchangeRateRequest.getTargetCurrency())
        .map(exchangeRate -> {
          BigDecimal rate = exchangeRate.getRate();
          BigDecimal amount = exchangeRateRequest.getAmount();
          return ExchangeRateResponse.builder()
              .amount(amount.multiply(rate).setScale(3, RoundingMode.HALF_UP))
              .rate(rate)
              .request(ExchangeRateResponse.Request.builder()
                  .amount(exchangeRateRequest.getAmount())
                  .sourceCurrency(exchangeRateRequest.getSourceCurrency())
                  .targetCurrency(exchangeRateRequest.getTargetCurrency())
                  .build())
              .build();
        })
        .switchIfEmpty(Single.error(new RuntimeException("Currency Operation not supported")));
  }

  public Single<FindExchangeRateResponse> save(UpdateExchangeRateRequest request) {
    return exchangeRateRepository
        .save(exchangeRateMapper.toEntity(request))
        .map(exchangeRateMapper::fromEntity);
  }

  public Observable<FindExchangeRateResponse> findAll() {
    return exchangeRateRepository
        .findAll()
        .map(exchangeRateMapper::fromEntity);
  }
}
