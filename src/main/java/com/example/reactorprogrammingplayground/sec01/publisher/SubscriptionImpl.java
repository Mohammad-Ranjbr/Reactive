package com.example.reactorprogrammingplayground.sec01.publisher;

import com.github.javafaker.Faker;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubscriptionImpl implements Subscription {

    private int count = 0; // How many items have been sent so far? (count keeps track of how many emails have been sent so that we can finally declare the completion of the task with onComplete().)
    private final Faker faker;
    private boolean isCancelled;
    private static final int MAX_ITEMS = 10;
    private final Subscriber<? super String> subscriber;
    private static final Logger log = LoggerFactory.getLogger(SubscriptionImpl.class);

    public SubscriptionImpl(Subscriber<? super String> subscriber) {
        this.faker = Faker.instance();
        this.subscriber = subscriber;
    }

    @Override
    public void request(long n) {
        if(isCancelled) return; // If it has already been canceled, do nothing
        log.info("subscriber has requested  {} items", n);
        for (int i = 0; i < n && count < MAX_ITEMS; i++) {
            count++;
            this.subscriber.onNext(this.faker.internet().emailAddress());
        }
        if(count == MAX_ITEMS) { // Is all the data gone?
            log.info("no more data to produce");
            this.subscriber.onComplete(); // end signal
            this.isCancelled = true; // We won't send anything else.
        }
    }

    @Override
    public void cancel() {
        this.isCancelled = true;
        log.info("subscriber has canceled");
    }

}
