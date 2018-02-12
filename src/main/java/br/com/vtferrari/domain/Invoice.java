package br.com.vtferrari.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class Invoice {
    private String client;
    private List<Product> product;
    private BigDecimal amount;
}
