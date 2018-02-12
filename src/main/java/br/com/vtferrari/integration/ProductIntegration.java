package br.com.vtferrari.integration;

import br.com.vtferrari.domain.Product;
import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProductIntegration {
    public static List<Product> all() {
        try {
            final String csv = HttpClient.newHttpClient()
                    .send(HttpRequest.newBuilder()
                                    .uri(new URI("https://raw.githubusercontent.com/vtferrari/primeiras-impressoes-java-9/master/products.csv"))
                                    .GET().build(),
                            HttpResponse.BodyHandler.asString()).body();

            return Stream.of(csv.split("\n"))
                    .map(ProductIntegration::create)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível conectar ", e);
        }
    }

    private static Product create(String line) {
        final String[] split = line.split(",");
        final String name = split[0];
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        final LocalDateTime dateTime = LocalDateTime.parse(split[1], formatter);
        final BigDecimal price = new BigDecimal(split[2]);
        return new Product(name, dateTime, price);
    }

    public static Optional<Product> findSimilar(Product product) {
        return ProductIntegration.all().stream()
                .filter(p -> p.getName().equals(product.getName()))
                .filter(p -> p.getPrice().compareTo(product.getPrice())>0)
                .findAny();
    }

}
