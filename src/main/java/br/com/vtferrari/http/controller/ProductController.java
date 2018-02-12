package br.com.vtferrari.http.controller;

import br.com.vtferrari.domain.Product;
import br.com.vtferrari.http.resource.ProductResource;
import br.com.vtferrari.usecase.InvoiceProductByPosition;
import br.com.vtferrari.usecase.ObtainAllProducts;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {
    private InvoiceProductByPosition invoiceProductByPosition;
    private ObtainAllProducts obtainAllProducts;

    @GetMapping
    public List<Product> listAll() {
        return obtainAllProducts.execute();
    }

    @PostMapping
    public ProductResource invoice(@RequestBody ProductResource productResource) {
        return new ProductResource(invoiceProductByPosition.execute(productResource.getProduct(), productResource.getClientName()));
    }
}
