package com.gustavofernandez.exchangerate.repository;

import com.gustavofernandez.exchangerate.controller.dto.ExchangeRateResponse;
import com.gustavofernandez.exchangerate.repository.entity.ExchangeRate;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ExchangeRateRepository {

  private final ExchangeRateJpaRepository exchangeRateJpaRepository;

  public Maybe<ExchangeRate> find(String sourceCurrency, String targetCurrency) {
    return exchangeRateJpaRepository.findBySourceCurrencyAndTargetCurrency(
        sourceCurrency, targetCurrency)
        .map(Maybe::just)
        .orElse(Maybe.empty());
  }

  public Single<ExchangeRate> save(ExchangeRate exchangeRate) {
    exchangeRateJpaRepository
        .findBySourceCurrencyAndTargetCurrency(exchangeRate.getSourceCurrency(), exchangeRate.getTargetCurrency())
        .ifPresent(er -> exchangeRate.setId(er.getId()));
    return Single.just(exchangeRateJpaRepository.save(exchangeRate));
  }

  public Observable<ExchangeRate> findAll() {
    return Observable.fromIterable(exchangeRateJpaRepository.findAll());
  }
}
