package com.example.reactorprogrammingplayground.sec03;

import com.example.reactorprogrammingplayground.common.Util;
import reactor.core.publisher.Flux;

public class Lec02MultipleSubscribers {
    public static void main(String[] args) {

        var flux = Flux.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        //A Flux can have multiple subscribers.
        //filter(): To filter items based on a specific condition (such as only even numbers).
        //map(): To transform items, such as adding something to them.

        flux.subscribe(Util.subscriber("sub1"));
        //The second subscriber is interested in receiving only items greater than 10.
        //Since none of the items meet this condition, this subscriber will not receive any items.
        //And will receive the onComplete signal directly.
        flux
                .filter(i -> i > 10)
                .subscribe(Util.subscriber("sub2"));
        //This way we can control the behavior of each subscriber individually.
        flux
                .filter(i -> i % 2 == 0) // This subscriber may only be interested in even numbers.
                .map(i -> i + "A")
                .subscribe(Util.subscriber("sub3"));

    }
}
