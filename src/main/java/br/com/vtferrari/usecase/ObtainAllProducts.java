package br.com.vtferrari.usecase;

import br.com.vtferrari.domain.Product;
import br.com.vtferrari.integration.ProductIntegration;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ObtainAllProducts {
    public List<Product> execute() {
        return ProductIntegration.all();
    }
}
