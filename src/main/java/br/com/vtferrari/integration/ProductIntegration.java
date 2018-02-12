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
            String csv = HttpClient.newHttpClient()
                    .send(HttpRequest.newBuilder()
                                    .uri(new URI("https://turini.github.io/livro-java-9/books.csv"))
                                    .GET().build(),
                            HttpResponse.BodyHandler.asString()).body();

            return Stream.of(csv.split("\n"))
                    .map(ProductIntegration::create)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new RuntimeException("Não foi possível conectar ", e);
        }
    }

    public static Product create(String line) {
        String[] split = line.split(",");
        String name = split[0];
        LocalDateTime dateTime = LocalDateTime.parse( split[1], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        BigDecimal price = new BigDecimal(split[2]);
        return new Product(name, dateTime, price);
    }

}
