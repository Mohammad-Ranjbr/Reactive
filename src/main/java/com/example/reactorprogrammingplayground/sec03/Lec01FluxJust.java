package com.example.reactorprogrammingplayground.sec03;

import com.example.reactorprogrammingplayground.common.Util;
import reactor.core.publisher.Flux;

public class Lec01FluxJust {
    public static void main(String[] args) {

        //Flux can publish 0, 1, or a large number (N) of items, and eventually send either a complete or error signal.
        //A Flux can also be an endless stream of messages.
        //In such a case, depending on the application, we may never receive a complete or error signal.
        //The Flux class also has a method called just that we can use to quickly create a Flux of arbitrary items.
        //These items are what Flux will publish as a Publisher.

        Flux.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .subscribe(Util.subscriber());

    }
}
