package com.example.reactorprogrammingplayground.sec05;

import com.example.reactorprogrammingplayground.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.function.UnaryOperator;

public class Lec10Transform {
    private static final Logger log = LoggerFactory.getLogger(Lec10Transform.class);

    record Customer(int id, String name) {
    }

    record PurchaseOrder(String productName, int price, int quantity) {
    }

    public static void main(String[] args) {

        //Repeating steps in several different places
        //Suppose you have several different workflows such as:
        //Create Order, Modify Order, Cancel Order
        //And all these workflows have a series of similar steps. For example:
        //Get and validate OrderId, log for debug, get customer information, etc.
        //These are repeated in several different places
        //Solution: Use transform
        //transform allows you to: Write a series of repeated operators separately and use them anywhere in Flux or Mono
        //Avoid repeating code Repeated steps are written only once

        demo2();

    }

    private static Flux<Customer> getCustomers() {
        return Flux.range(1, 3)
                .map(i -> new Customer(i, Util.faker().name().firstName()));
    }

    private static Flux<PurchaseOrder> getPurchaseOrders() {
        return Flux.range(1, 5)
                .map(i -> new PurchaseOrder(Util.faker().commerce().productName(), i, i * 10));
    }

    private static void demo1() {
        getCustomers()
                .doOnNext(i -> log.info("received: {}", i))
                .doOnComplete(() -> log.info("completed"))
                .doOnError(e -> log.error("error"))
                .subscribe();

        getPurchaseOrders()
                .doOnNext(i -> log.info("received: {}", i))
                .doOnComplete(() -> log.info("completed"))
                .doOnError(e -> log.error("error"))
                .subscribe();
    }

    private static <T> UnaryOperator<Flux<T>> addDebugger() {
        return flux -> flux
                .doOnNext(i -> log.info("received: {}", i))
                .doOnComplete(() -> log.info("completed"))
                .doOnError(e -> log.error("error"));
    }

    private static void demo2() {
        getCustomers()
                .transform(addDebugger())
                .subscribe();

        getPurchaseOrders()
                .transform(addDebugger())
                .subscribe();
    }

}
