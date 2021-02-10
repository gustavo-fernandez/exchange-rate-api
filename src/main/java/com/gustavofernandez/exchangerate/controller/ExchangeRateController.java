package com.gustavofernandez.exchangerate.controller;

import com.gustavofernandez.exchangerate.controller.dto.ExchangeRateRequest;
import com.gustavofernandez.exchangerate.controller.dto.ExchangeRateResponse;
import com.gustavofernandez.exchangerate.controller.dto.FindExchangeRateResponse;
import com.gustavofernandez.exchangerate.controller.dto.UpdateExchangeRateRequest;
import com.gustavofernandez.exchangerate.security.JwtAction;
import com.gustavofernandez.exchangerate.service.ExchangeRateService;
import io.reactivex.Observable;
import io.reactivex.Single;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.math.BigDecimal;

@Validated
@RestController
@RequiredArgsConstructor
public class ExchangeRateController {

  private final ExchangeRateService exchangeRateService;

  @JwtAction("exchange-rate-operation")
  @GetMapping("/exchange-rate/{amount}/{from}/{to}")
  public Single<ExchangeRateResponse> exchangeRate(
      @PathVariable @Min(0) BigDecimal amount,
      @PathVariable @Length(min = 3, max = 3) String from,
      @PathVariable @Length(min = 3, max = 3) String to) {
    return exchangeRateService.find(ExchangeRateRequest.builder()
        .amount(amount)
        .sourceCurrency(from)
        .targetCurrency(to)
        .build());
  }

  @JwtAction("exchange-rate-save")
  @PostMapping("/exchange-rate")
  public Single<FindExchangeRateResponse> save(@RequestBody UpdateExchangeRateRequest request) {
    return exchangeRateService.save(request);
  }

  @JwtAction("exchange-rate-find-all")
  @GetMapping("/exchange-rate")
  public Observable<FindExchangeRateResponse> findAll() {
    return exchangeRateService.findAll();
  }

}
