package com.gustavofernandez.exchangerate.mapper;

import com.gustavofernandez.exchangerate.controller.dto.FindExchangeRateResponse;
import com.gustavofernandez.exchangerate.controller.dto.UpdateExchangeRateRequest;
import com.gustavofernandez.exchangerate.repository.entity.ExchangeRate;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExchangeRateMapper {

  FindExchangeRateResponse fromEntity(ExchangeRate source);

  ExchangeRate toEntity(UpdateExchangeRateRequest source);

}
