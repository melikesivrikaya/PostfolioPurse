package com.portfolio.purse.controller;

import com.portfolio.purse.model.CurrencyEntity;
import com.portfolio.purse.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/currency")
public class CurrencyController {
    private final CurrencyService currencyService;
    @PostMapping
    public CurrencyEntity createCurrency(@RequestBody CurrencyEntity currency) {
        return currencyService.create(currency);
    }
    @GetMapping
    public List<CurrencyEntity> getAll() {
        return currencyService.getAll();
    }

}
