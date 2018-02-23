package br.com.vtferrari;

import br.com.vtferrari.domain.Product;
import br.com.vtferrari.integration.ProductIntegration;
import br.com.vtferrari.integration.TransmitInvoice;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

@SpringBootApplication
public class JavanoveApplication {

    public static void main(String[] args) {

        SpringApplication.run(JavanoveApplication.class, args);
    }

}
