package com.example.reactorprogrammingplayground.sec04;

import com.example.reactorprogrammingplayground.common.Util;
import com.example.reactorprogrammingplayground.sec04.helper.NameGenerator;
import reactor.core.publisher.Flux;

public class Lec02FluxCreateRefactor {
    public static void main(String[] args) {

        //The reactor executes the create(...) method.
        //The generator, which is an object of the NameGenerator class, is passed to create as a Consumer<FluxSink<String>> .
        //The reactor itself creates an object of type FluxSink<String> (to send data).
        //Then, it passes this sink to the accept(...) method of the generator.
        var generator = new NameGenerator();
        var flux = Flux.create(generator);
        flux.subscribe(Util.subscriber());

        for(int i=0; i<10; i++) {
            generator.generate();
        }

    }
}
