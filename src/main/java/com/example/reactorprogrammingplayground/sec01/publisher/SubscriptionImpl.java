package com.example.reactorprogrammingplayground.sec01.publisher;

import com.example.reactorprogrammingplayground.sec01.subscriber.SubscriberImpl;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubscriptionImpl implements Subscription {

    private Subscriber<? super String> subscriber;
    private static final Logger log = LoggerFactory.getLogger(SubscriberImpl.class);

    public SubscriptionImpl(Subscriber<? super String> subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    public void request(long n) {

    }

    @Override
    public void cancel() {

    }

}
