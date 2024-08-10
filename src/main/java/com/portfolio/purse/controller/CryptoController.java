package com.portfolio.purse.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.purse.model.CurrencyEntity;
import com.portfolio.purse.repository.CurrencyRepository;
import com.portfolio.purse.service.CoinMarketCapService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/crypto")
public class CryptoController {

    private final CoinMarketCapService coinMarketCapService;
    private final CurrencyRepository currencyRepository;

    // ID ye göre getiriyor
    @GetMapping("/{id}")
    public Mono<String> getCryptoInfo(@PathVariable String id) {
        return coinMarketCapService.getCryptoInfo(id);
    }
    //Currency bilgilerini database e setliyo
    @GetMapping("/currency")
    public Mono<String> callCurrencyList(){
        // Gelen veriler curenyies tablosuna eklenecek
        Mono<String> mono = coinMarketCapService.setCurrency();

        ObjectMapper objectMapper = new ObjectMapper();
        List<CurrencyEntity> currencies = new ArrayList<>();
        mono.map(json -> {
            try {
                JsonNode rootNode = objectMapper.readTree(json);
                JsonNode dataNode = rootNode.path("data");

                // data kısmını Java nesnelerine dönüştür

                Iterator<JsonNode> elements = dataNode.elements();

                while (elements.hasNext()) {
                    JsonNode currencyNode = elements.next();
                    CurrencyEntity currency = objectMapper.treeToValue(currencyNode, CurrencyEntity.class);
                    currencies.add(currency);
                }

                // Sonuçları yazdır
                currencies.forEach(currency -> {
                    System.out.println("ID: " + currency.getId());
                    System.out.println("Name: " + currency.getName());
                    System.out.println("Sign: " + currency.getSign());
                    System.out.println("Symbol: " + currency.getSymbol());
                    currencyRepository.save(currency);
                });



            } catch (IOException e) {
                e.printStackTrace();
            }

            return currencies;
        });



        return mono;
    }

    public Mono<CurrencyEntity> convertToMyObject(Mono<String> monoString) {
        ObjectMapper objectMapper = new ObjectMapper();
        return monoString.map(jsonString -> {
            try {
                return objectMapper.readValue(jsonString, CurrencyEntity.class);
            } catch (Exception e) {
                throw new RuntimeException("JSON parsing error", e);
            }
        });
    }
}
