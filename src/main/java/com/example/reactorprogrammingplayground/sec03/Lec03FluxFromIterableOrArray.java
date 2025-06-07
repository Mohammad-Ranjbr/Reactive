package com.example.reactorprogrammingplayground.sec03;

import com.example.reactorprogrammingplayground.common.Util;
import reactor.core.publisher.Flux;

import java.util.List;

public class Lec03FluxFromIterableOrArray {
    public static void main(String[] args) {

        //Flux.fromIterable(list) Converts anything that is Iterable (like List or Set) to Flux
        var list = List.of("a", "b", "c", "d", "e", "f", "g", "h");
        Flux.fromIterable(list)
                .subscribe(Util.subscriber());

        //Flux.fromArray(array) Convert a Java array to Flux
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        Flux.fromArray(array)
                .subscribe(Util.subscriber());

    }
}
