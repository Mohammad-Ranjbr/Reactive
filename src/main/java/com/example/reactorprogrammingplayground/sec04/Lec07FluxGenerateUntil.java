package com.example.reactorprogrammingplayground.sec04;

import com.example.reactorprogrammingplayground.common.Util;
import reactor.core.publisher.Flux;

public class Lec07FluxGenerateUntil {
    public static void main(String[] args) {

        demo2();

    }

    private static void demo1() {
        Flux.generate(synchronousSink -> {
            var country = Util.faker().country().name();
            synchronousSink.next(country);
            if (country.equalsIgnoreCase("canada")) {
                synchronousSink.complete();
            }
        }).subscribe(Util.subscriber());
    }

    private static void demo2() {
        //takeUntil handles the stopping condition.
        //There is no need to manually complete(), as it will cancel itself when the condition is met.
        Flux.<String>generate(synchronousSink -> {
                    var country = Util.faker().country().name();
                    synchronousSink.next(country);
                })
                .takeUntil(c -> c.equalsIgnoreCase("canada"))
                .subscribe(Util.subscriber());
    }

}
