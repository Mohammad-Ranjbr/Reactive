package com.example.reactorprogrammingplayground.sec04;

import com.example.reactorprogrammingplayground.common.Util;
import reactor.core.publisher.Flux;

public class Lec05TakeOperator {
    public static void main(String[] args) {

        Flux.range(1, 10)
                .take(3)
                .subscribe(Util.subscriber());

        take();

    }

    //Initial request: The take operator itself requests a large amount from the upstream, because it does not know how many values the upstream will send.
    //Value broadcast: Only passes the first n values upstream.
    //Cancel: After reaching n values, the take cancels the upstream flow.
    //Completion: Then the downstream (i.e. subscriber) receives the onComplete signal.

    private static void take(){
        //Similar to limit(n) in Java Stream
        //It only takes the first n values and then cancels.
        Flux.range(1, 10)
                .log("take")
                .take(3)
                .log("sub")
                .subscribe(Util.subscriber());
    }

    private static void takeWhile(){
        //Gets values as long as the condition is true.
        //Cancels as soon as the condition is not true.
        Flux.range(1, 10)
                .log("take")
                .takeWhile(i -> i < 5)
                .log("sub")
                .subscribe(Util.subscriber());
    }

    private static void takeUntil(){
        //It receives values until the condition is met.
        //Once the condition is met, it emits that value and then stops.
        Flux.range(1, 10)
                .log("take")
                .takeUntil(i -> i >= 5)
                .log("sub")
                .subscribe(Util.subscriber());
    }

}
