package com.example.reactorprogrammingplayground.sec05;

import com.example.reactorprogrammingplayground.common.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class Lec04Delay {
    public static void main(String[] args) throws InterruptedException {

        //demo1();

        demo2();
        Util.sleepSeconds(10);

    }

    private static void demo1(){
        Flux.range(1, 10)
                .log()
                .subscribe(Util.subscriber());
    }

    private static void demo2(){
        //What is delayElements(Duration)?
        //This operator in Reactive Streams causes each item to be sent to the Subscriber with a specified delay.
        //Normally, without delayElements, Flux.range(1,10) would generate and send all items immediately.
        //But with delayElements(Duration.ofSeconds(1)), instead of sending them all at once, one item will reach the Subscriber every second.
        //At every specified interval (e.g. every 1 second), a request for a new item is sent
        //request(1)
        //Instead of initially requesting(unbounded) (i.e. requesting all items), it just proceeds step-by-step
        Flux.range(1, 10)
                .log()
                .delayElements(Duration.ofSeconds(1))
                .subscribe(Util.subscriber());
    }

}
