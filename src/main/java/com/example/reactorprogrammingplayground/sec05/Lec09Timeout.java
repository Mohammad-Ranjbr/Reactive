package com.example.reactorprogrammingplayground.sec05;

import com.example.reactorprogrammingplayground.common.Util;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class Lec09Timeout {
    public static void main(String[] args) throws InterruptedException {

        //When working with Mono or Flux and the data source (such as an external service or database) may be slow to respond, you don't want to wait forever!
        //Using timeout() you can say: if no data arrives within a certain time, abort and use another method (e.g. default value, or alternative service).
        //timeout(Duration) If no signal (value, error or end) is received within this interval, a TimeoutException is thrown.
        demo3();

    }

    private static Mono<String> getProductName(){
        return Mono.fromSupplier(() -> "Service1 - " + Util.faker().commerce().productName())
                .delayElement(Duration.ofMillis(1900));
    }

    private static Mono<String> fallback(){
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
                .doFirst(() -> System.out.println("do first"))
                .subscribe(Util.subscriber());

        Util.sleepSeconds(4);
    }

}
