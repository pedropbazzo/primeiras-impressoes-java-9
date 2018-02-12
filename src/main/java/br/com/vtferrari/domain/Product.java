package br.com.vtferrari.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Product {
    private String name;
    private LocalDateTime date;
    private BigDecimal price;
}
