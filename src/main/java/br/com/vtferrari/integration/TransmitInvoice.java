package br.com.vtferrari.integration;

import br.com.vtferrari.domain.Invoice;
import br.com.vtferrari.domain.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.SubmissionPublisher;

public class TransmitInvoice {


    private SubmissionPublisher<Invoice> publisher;

    public TransmitInvoice() {
        this.publisher = new SubmissionPublisher<>();
        publisher.subscribe(new InvoiceSubscribe());
    }

    public void emit(String clientName, List<Product> product) {

        publisher.submit(new Invoice(clientName, product, product.stream().map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add)));
    }

    public void close() {
        this.publisher.close();
    }


}
