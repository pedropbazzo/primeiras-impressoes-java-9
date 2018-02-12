package br.com.vtferrari.http.resource;

import br.com.vtferrari.domain.Product;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductResource {
    private Long product;
    private String productName;
    private BigDecimal price;
    private String clientName;
    private ProductResource sugestion;


    public ProductResource(List<Product> execute) {
        productName = execute.get(0).getName();
        price = execute.get(0).getPrice();
        execute.stream().skip(1).forEach(product -> {
                    sugestion = new ProductResource(execute.get(1));
                });
    }

    public ProductResource(Product execute) {
        productName = execute.getName();
        price = execute.getPrice();
    }
}
