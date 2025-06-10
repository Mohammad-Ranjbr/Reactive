package com.example.reactorprogrammingplayground.sec05;

import com.example.reactorprogrammingplayground.common.Util;
import reactor.core.publisher.Flux;

public class Lec07DefaultIfEmpty {
    public static void main(String[] args) {

        //In many cases, the Publisher may not send any value (onComplete without onNext).
        //In this case, you need to decide what behavior to show. defaultIfEmpty() : If no data is published, publish a hard-coded value.
        Flux.range(1, 10)
                .filter(i -> i > 10)
                .defaultIfEmpty(50)
                .subscribe(Util.subscriber());

    }
}
