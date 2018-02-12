package br.com.vtferrari.integration;

import br.com.vtferrari.domain.Invoice;
import br.com.vtferrari.domain.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.SubmissionPublisher;

@Service
@AllArgsConstructor
public class TransmitInvoice {

    private SubmissionPublisher<Invoice> publisher;

    public TransmitInvoice() {
        publisher = new SubmissionPublisher<>();
        publisher.subscribe(new InvoiceSubscribe());
    }

    public void emit(String clientName, List<Product> product) {

        final BigDecimal products = product
                .stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        final Invoice invoice = new Invoice(clientName, product, products);
        publisher.submit(invoice);
    }

    public void close() {
        this.publisher.close();
    }


}
