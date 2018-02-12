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

    private void main() {
        System.out.println("\nLista de livros disponíveis \n");

        List<Product> products = ProductIntegration.all();

        IntStream.range(0, products.size())
                .forEach(i -> {
                    System.out.println(i + " - " + products.get(i).getName());
                });

        Scanner scanner = new Scanner(System.in);

        System.out.print("\nDigite o numero do livro que quer comprar: ");

        try {
            int number = scanner.nextInt();
            Product product = products.get(number);

            System.out.println("\nO produto escolhido foi: " + product.getName());

            System.out.print("\nInforme seu nome, para que possamos emitir a nota fiscal: ");

            scanner = new Scanner(System.in);
            String name = scanner.nextLine();
            TransmitInvoice emissor = new TransmitInvoice();
            emissor.emit(name, List.of(product));

            System.out.println("Obrigado!");


            // segura a execução para o código assíncrono terminar
            System.out.println("Aperte o enter para sair");
            new Scanner(System.in).nextLine();

            emissor.close();

        } catch (Exception e) {
            System.err.println("Ops, aconteceu um erro: " + e);
        }
    }

}
