package br.com.vtferrari.usecase;

import br.com.vtferrari.domain.Product;
import br.com.vtferrari.integration.TransmitInvoice;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class InvoiceProductByPosition {
    private ObtainSimilarProduct similarProduct;
    private ObtainAllProducts obtainAllProducts;
    private TransmitInvoice emissor;

    public List<Product> execute(Long position, String client) {
        final Product product = obtainAllProducts.execute().get(position.intValue());
        emissor.emit(client, List.of(product));
        List<Product> products = new ArrayList<>();
        products.add(product);
        similarProduct.execute(product)
                .ifPresentOrElse(products::add, () -> System.out.println("Só pra mostrar o novo metodo do Optional"));
        return products;
    }


}
