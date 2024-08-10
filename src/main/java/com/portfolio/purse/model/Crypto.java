package com.portfolio.purse.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Entity
@Table(name = "cryptos")
public class Crypto {
    @Id
    private int id;
    private String name;
    private String symbol;

    private Long marketPairs; // Piyasa çiftleri
    private Long maxSupply; // Max Arz
    private Long totalSupply; // Toplam Arz
    private Long circulatingSupply; // Dolaşımdaki arz
    private Long price;
}
