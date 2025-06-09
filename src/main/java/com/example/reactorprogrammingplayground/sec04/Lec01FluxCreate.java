package com.example.reactorprogrammingplayground.sec04;

import com.example.reactorprogrammingplayground.common.Util;
import reactor.core.publisher.Flux;

public class Lec01FluxCreate {
    public static void main(String[] args) {

        //In the previous sections, we learned how to use methods like Flux.just() or Flux.fromIterable() .
        //These are good for data that is already in memory, but they are not suitable if we want to generate data at runtime (for example, in a loop or based on a specific condition).
        //Here, Flux.create() is our solution.
        //fluxSink.next(...) is used to send data to the subscriber.
        //fluxSink.complete() sends the completion signal.
        //fluxSink is an interface in the Project Reactor library (used for reactive programming in Java).
        //When you use the Flux.create() method, it gives you a callback that gives you an object of type FluxSink<T> . This object is a tool with which you can programmatically emit data to Flux.

        Flux.create(sink -> {
            sink.next(1);
            sink.next(2);
            sink.next(3);
            sink.complete();
        }).subscribe(Util.subscriber());

        Flux.create(sink -> {
            for(int i=0; i<10; i++) {
                sink.next(Util.faker().country().name());
            }
            sink.complete();
        }).subscribe(Util.subscriber());

        Flux.create(sink -> {
            String country;
            do {
                country = Util.faker().country().name();
                sink.next(country);
            } while(!country.equalsIgnoreCase("canada"));
            sink.complete();
        }).subscribe(Util.subscriber());

    }
}
