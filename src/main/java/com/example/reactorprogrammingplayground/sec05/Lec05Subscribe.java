package com.example.reactorprogrammingplayground.sec05;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class Lec05Subscribe {
    private static final Logger log = LoggerFactory.getLogger(Lec05Subscribe.class);
    public static void main(String[] args) {

        //You specify only what you need
        //Simpler and more readable for common cases instead of the previous case where we used the full subscriber
        Flux.range(1, 10)
                .doOnNext(i -> log.info("received: {}", i))
                .doOnComplete(() -> log.info("completed"))
                .doOnError(e -> log.error("error", e))
                .subscribe();

    }
}
