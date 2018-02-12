package br.com.vtferrari.usecase;

import br.com.vtferrari.domain.Product;
import br.com.vtferrari.integration.ProductIntegration;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ObtainSimilarProduct {
    public Optional<Product> execute(Product product) {
        return ProductIntegration.findSimilar(product);
    }
}
