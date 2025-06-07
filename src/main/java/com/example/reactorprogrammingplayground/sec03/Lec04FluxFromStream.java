package com.example.reactorprogrammingplayground.sec03;

import com.example.reactorprogrammingplayground.common.Util;
import reactor.core.publisher.Flux;

import java.util.List;

public class Lec04FluxFromStream {
    public static void main(String[] args) {

        //Just as we can convert a list or array to a Flux, we can also convert a Java Stream to a Flux.
        //First we create a list
        //Now from this list, we create a Stream
        //And then we create a Flux
        var list = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        var stream = list.stream();

        //Now if we define a subscriber everything works fine.
        //But if we add a second subscriber we will get an error here
        //This error comes from Java Stream, not Flux.
        //In Java, Stream can be used only once. Once you work with forEach or any other operation on stream, you cannot use it again.

//        var flux = Flux.fromStream(stream);
//
//        flux.subscribe(Util.subscriber("sub1"));
//        flux.subscribe(Util.subscriber("sub2"));

        //Solution: Use Supplier<Stream>
        //To create a new stream every time a subscriber registers, we need to use Supplier.
        var flux = Flux.fromStream(() -> list.stream());

        flux.subscribe(Util.subscriber("sub1"));
        flux.subscribe(Util.subscriber("sub2"));

    }
}
