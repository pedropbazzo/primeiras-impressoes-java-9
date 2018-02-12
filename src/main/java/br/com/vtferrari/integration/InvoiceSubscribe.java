package br.com.vtferrari.integration;

import br.com.vtferrari.domain.Invoice;

import java.util.concurrent.Flow;

public class InvoiceSubscribe implements Flow.Subscriber<Invoice> {

    private Flow.Subscription subscription;

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(Invoice nf) {
        GovernmentWebService.emit(nf);
        subscription.request(1);
    }

    @Override
    public void onError(Throwable t) {
        t.printStackTrace();
    }

    @Override
    public void onComplete() {
        System.out.println(
                "Todas as notas foram emitidas");
    }
}
