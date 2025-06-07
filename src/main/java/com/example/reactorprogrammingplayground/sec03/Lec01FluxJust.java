package com.example.reactorprogrammingplayground.sec03;

import com.example.reactorprogrammingplayground.common.Util;
import reactor.core.publisher.Flux;

public class Lec01FluxJust {
    public static void main(String[] args) {

        Flux.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .subscribe(Util.subscriber());

    }
}
