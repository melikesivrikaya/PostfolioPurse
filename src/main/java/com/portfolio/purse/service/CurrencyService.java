package com.portfolio.purse.service;

import com.portfolio.purse.model.CurrencyEntity;
import com.portfolio.purse.repository.CurrencyRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyService {
    private final CurrencyRepository currencyRepository;
    public CurrencyEntity create(CurrencyEntity currency) {
        return currencyRepository.save(currency);
    }

    public List<CurrencyEntity> getAll() {
        return currencyRepository.findAll();
    }
}
