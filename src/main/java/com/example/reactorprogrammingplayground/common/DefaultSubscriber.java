package com.example.reactorprogrammingplayground.common;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultSubscriber<T> implements Subscriber<T> {

    public final String name;
    private static final Logger log = LoggerFactory.getLogger(DefaultSubscriber.class);

    public DefaultSubscriber(String name) {
        this.name = name;
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        //In onSubscribe() , we make the Subscriber receive all the items without having to manually request their number. This is simple and suitable for demos.
        subscription.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(T item) {
        log.info("{} received: {}", name, item);
    }

    @Override
    public void onError(Throwable t) {
        log.error("{} error", name, t);
    }

    @Override
    public void onComplete() {
        log.info("{} received completed!", name);
    }

    //The DefaultSubscriber<T> class is created, which is an implementation of Subscriber<T>.
    //The generic type T is used, so that it can handle any type of data.
    //In the onNext(T item) method, the received value is printed along with the subscriber name.
    //Using the name (which is received from the constructor), it is possible to identify which subscriber has received which data.
    //In the onSubscribe(Subscription s) method, s.request(Long.MAX_VALUE) is used to request all the data.
    //In a helper class called Util, methods are written to easily create the DefaultSubscriber.

}
