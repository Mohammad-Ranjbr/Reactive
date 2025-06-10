package com.example.reactorprogrammingplayground.sec05;

import com.example.reactorprogrammingplayground.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class Lec09Timeout {
    private static final Logger log = LoggerFactory.getLogger(Lec09Timeout.class);

    public static void main(String[] args) throws InterruptedException {

        //When working with Mono or Flux and the data source (such as an external service or database) may be slow to respond, you don't want to wait forever!
        //Using timeout() you can say: if no data arrives within a certain time, abort and use another method (e.g. default value, or alternative service).
        //timeout(Duration) If no signal (value, error or end) is received within this interval, a TimeoutException is thrown.
        demo4();

    }

    private static Mono<String> getProductName() {
        return Mono.fromSupplier(() -> "Service1 - " + Util.faker().commerce().productName())
                .delayElement(Duration.ofMillis(1900));
    }

    private static Mono<String> fallback() {
        return Mono.fromSupplier(() -> "fallback - " + Util.faker().commerce().productName())
                .delayElement(Duration.ofMillis(300));
    }

    private static void demo1() throws InterruptedException {
        getProductName()
                .timeout(Duration.ofSeconds(1))
                .subscribe(Util.subscriber());

        Util.sleepSeconds(4);
    }

    private static void demo2() throws InterruptedException {
        getProductName()
                .timeout(Duration.ofSeconds(1))
                .onErrorReturn("fallback")
                .subscribe(Util.subscriber());

        Util.sleepSeconds(4);
    }

    private static void demo3() throws InterruptedException {
        getProductName()
                .timeout(Duration.ofSeconds(2), fallback())
                .onErrorReturn("fallback")
                .doFirst(() -> log.info("do first"))
                .subscribe(Util.subscriber());

        Util.sleepSeconds(4);
    }

    private static void demo4() throws InterruptedException {
        //Which timeout is used?
        //The one closest to subscribe. That means that 200ms timeout is used. If there is no response within that time, that will cause an error.
        Mono<String> mono = getProductName().timeout(Duration.ofSeconds(1));
        mono.timeout(Duration.ofMillis(200))
                .onErrorReturn("fallback")
                .subscribe();

        Util.sleepSeconds(4);
    }

}
