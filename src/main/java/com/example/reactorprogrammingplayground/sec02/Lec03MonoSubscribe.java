package com.example.reactorprogrammingplayground.sec02;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class Lec03MonoSubscribe {

    private static final Logger log = LoggerFactory.getLogger(Lec03MonoSubscribe.class);

    public static void main(String[] args){

        var mono = Mono.just(1);
        //We only defined onNext. But there is no onComplete signal, because only the handler for onNext is given.
        //mono.subscribe(i -> log.info("received: {}", i));

        mono.subscribe(
                i -> log.info("received: {}", i),
                err -> log.error("error: ", err),
                () -> log.info("completed")
        );
        //Now we have covered all the main Subscriber signals.
        //We will see if there is an error or if the release is complete.
        //But then why is there no need for request()?
        //In our implementation, we would have called request(1), but not here!
        //Reason: The Reactive library itself internally calls subscription.request(Long.MAX_VALUE).
        //Because when subscribe(...) is called with the appropriate handler, it is assumed that you want to receive data.

        mono.subscribe(
                i -> log.info("received: {}", i),
                err -> log.error("error: ", err),
                () -> log.info("completed"),
                subscription -> subscription.cancel()
        );
        //If you cancel(), you will not receive any data.

        mono.subscribe(
                i -> log.info("received= {}", i),
                err -> log.error("error: ", err),
                () -> log.info("completed!"),
                subscription -> subscription.request(1)
        );
        //If you give request(1), a value will be received.

        var mono1 = Mono
                .just(1)
                .map(i -> i / 0);
        mono1.subscribe(
                i -> log.info("received: {}", i),
                err -> log.error("error: ", err),
                () -> log.info("completed"),
                subscription -> subscription.request(1)
        );
        //Catching an error using map()
        //The map method in Reactive Programming (and also in Java Streams) is used to transform data. That is,
        //when a piece of data is published from the Publisher, it is modified by map() before it reaches the subscriber.

    }
}
