package com.example.reactorprogrammingplayground.sec03;

import com.example.reactorprogrammingplayground.common.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class Lec09FluxInterval {
    public static void main(String[] args) throws InterruptedException {

        //What is Flux.interval?
        //It is a method of the Flux class that automatically generates Long numbers starting from 0 and sending them one by one at specified intervals.
        Flux.interval(Duration.ofMillis(500))
                .subscribe(Util.subscriber());

        //You can use the map() operator to change the content sent. For example, instead of a number, generate a random name each time.
        Flux.interval(Duration.ofMillis(500))
                .map(i -> Util.faker().name().fullName())
                .subscribe(Util.subscriber());

        //Flux.interval() continues infinitely by default.
        //If you only want to send, say, 3 values, use take()
        Flux.interval(Duration.ofMillis(500))
                .map(i -> Util.faker().commerce().productName())
                .take(3)
                .subscribe(Util.subscriber());

        //Since Reactive operates non-blocking, the main thread terminates immediately ,and you don't see anything in the output.
        Util.sleepSeconds(5);

    }
}
