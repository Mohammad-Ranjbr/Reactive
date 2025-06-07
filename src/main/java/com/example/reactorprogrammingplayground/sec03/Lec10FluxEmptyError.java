package com.example.reactorprogrammingplayground.sec03;

import com.example.reactorprogrammingplayground.common.Util;
import reactor.core.publisher.Flux;

public class Lec10FluxEmptyError {
    public static void main(String[] args) {

        //Creates a Flux that does not produce any items. Only the onComplete signal is sent.
        Flux.empty()
                .subscribe(Util.subscriber());

        //Generates a Flux that does not send any items and directly sends the onError signal with an error.
        Flux.error(new RuntimeException("oops"))
                .subscribe(Util.subscriber());

    }
}
