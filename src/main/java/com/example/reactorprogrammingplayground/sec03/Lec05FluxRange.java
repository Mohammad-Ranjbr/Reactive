package com.example.reactorprogrammingplayground.sec03;

import com.example.reactorprogrammingplayground.common.Util;
import reactor.core.publisher.Flux;

public class Lec05FluxRange {
    public static void main(String[] args) {

        //Simulating a for loop in reactive
        //Flux.range(start, count) produces count items from start

        //Because it starts at 3 and the number of items is 10, it continues until the number 12 (not just until 10).
        Flux.range(3, 10)
                .subscribe(Util.subscriber());

        Flux.range(1, 10) // Produce 10 numbers from 1 to 10
                .map(i -> Util.faker().name().firstName()) //Converts any number to a random name.
                .subscribe(Util.subscriber());

    }
}
