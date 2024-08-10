package com.portfolio.purse.service;

import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import org.springframework.beans.factory.annotation.Value;

@Service
@Data
public class CoinMarketCapService {

    @Value("${coinmarketcap.api.key}")
    private String apiKey;

    private final WebClient webClient;

    public CoinMarketCapService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://pro-api.coinmarketcap.com/v1/").build();
    }

    public Mono<String> getCryptoInfo(String cryptoId) {
        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path("cryptocurrency/quotes/latest")
                        .queryParam("id", cryptoId)
                        .build())
                .header("X-CMC_PRO_API_KEY", apiKey)
                .retrieve()
                .bodyToMono(String.class);
    }
    public Mono<String> setCurrency() {
        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path("fiat/map")
                        .build())
                .header("X-CMC_PRO_API_KEY", apiKey)
                .retrieve()
                .bodyToMono(String.class);
    }

}
