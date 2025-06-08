package com.example.reactorprogrammingplayground.sec03.helper;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StockPriceObserver implements Subscriber<Integer> {

    private int quantity = 0;
    private int balance = 1000;
    private Subscription subscription;
    private static final Logger log = LoggerFactory.getLogger(StockPriceObserver.class);

    //You need to create a subscriber with an initial balance of $1000 and implement the following behavior based on price changes:
    //Buy:
    //When the price goes below 90 → buy a share.
    //Each time you buy, the share price is deducted from your balance and your number of shares increases.
    //Sell & Exit:
    //When the price goes above 110 → sell all your shares.
    //After selling, cancel the subscription (because you are no longer interested in following).
    //You should use Flux because the service is sending continuous data.
    //You should use a custom Subscriber so that you can make decisions at each step (onNext, onComplete, onError, onSubscribe).

    @Override
    public void onSubscribe(Subscription subscription) {
        subscription.request(Long.MAX_VALUE);
        this.subscription = subscription;
    }

    @Override
    public void onNext(Integer price) {
        if(price < 90 && balance >= price) {
            quantity++;
            balance -= price;
            log.info("bought a stock at {}. total quantity: {}, remaining balance: {}", price, quantity, balance);
        } else if(price > 110 && quantity > 0){
            log.info("selling {} quantities at {}", quantity, price);
            balance = balance + (quantity * price);
            quantity = 0;
            subscription.cancel();
            log.info("profit: {}", (balance - 1000));
        }
    }

    @Override
    public void onError(Throwable throwable) {
        log.error("error", throwable);
    }

    @Override
    public void onComplete() {
        log.info("completed");
    }

}
