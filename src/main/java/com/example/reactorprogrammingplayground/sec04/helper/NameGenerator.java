package com.example.reactorprogrammingplayground.sec04.helper;

import com.example.reactorprogrammingplayground.common.Util;
import reactor.core.publisher.FluxSink;

import java.util.function.Consumer;

public class NameGenerator implements Consumer<FluxSink<String>> {

    //Putting all the data generation logic in a lambda inside Flux.create() may cause readability issues.
    //To avoid this problem, we create a separate class like NameGenerator or NameProducer that:
    //is an implementer of the Consumer<FluxSink<String>> interface.
    //In the accept(...) method, it receives the FluxSink object and stores it as a field.

    private FluxSink<String> sink;

    @Override
    public void accept(FluxSink<String> stringFluxSink) {
        this.sink = stringFluxSink;
    }

    //In the generate() method, it injects data into Flux using this.sink.next(...).
    public void generate() {
        this.sink.next(Util.faker().name().firstName());
    }

}
