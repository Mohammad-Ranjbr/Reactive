package com.example.reactorprogrammingplayground.sec04;

import com.example.reactorprogrammingplayground.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class Lec06FluxGenerate {
    private static final Logger logger = LoggerFactory.getLogger(Lec06FluxGenerate.class);

    public static void main(String[] args) {

        demo3();

    }

    private static void demo1() {
        //The Flux.generate method only allows you to pass one value (next) per execution.
        //But you passed two values in a row (1 and 2), which is not allowed.
        Flux.generate(synchronousSink -> {
            synchronousSink.next(1);
            synchronousSink.next(2);
            synchronousSink.complete();
        }).subscribe(Util.subscriber());
    }

    private static void demo2() {
        Flux.generate(synchronousSink -> {
            synchronousSink.next(1);
            synchronousSink.complete();
        }).subscribe(Util.subscriber());
    }

    private static void demo3() {
        //Flux generate
        //invokes the given lambda expression again and again based on downstream demand
        //we can emit only one value at a time
        //will stop when complete method is invoked
        //will stop when error method is invoked
        //will stop downstream cancels
        //The Flux.generate(...) function actually acts like a loop, but you don't actually write the loop yourself — Flux.generate itself loops behind the scenes, based on demand.
        //Flux.generate takes a function that produces a value (next) each time.
        //This function runs over and over again as long as there is a request from the subscriber.
        //When does it stop? On complete(), error(), or when the subscriber says "enough" (like take)
        Flux.generate(synchronousSink -> {
                    logger.info("invoked");
                    synchronousSink.next(1);
                    //synchronousSink.complete();
                    //synchronousSink.error(new RuntimeException("oops"));
                })
                .take(4)
                .subscribe(Util.subscriber());
    }

}
