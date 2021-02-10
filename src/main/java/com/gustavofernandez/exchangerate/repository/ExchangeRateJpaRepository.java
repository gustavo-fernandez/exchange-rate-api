package com.gustavofernandez.exchangerate.repository;

import com.gustavofernandez.exchangerate.repository.entity.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExchangeRateJpaRepository extends JpaRepository<ExchangeRate, Long> {

  Optional<ExchangeRate> findBySourceCurrencyAndTargetCurrency(
      String sourceCurrency, String targetCurrency);

}
