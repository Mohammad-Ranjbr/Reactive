package com.example.reactorprogrammingplayground.sec02;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Stream;

public class Lec01LAzyStream {

    private static final Logger logger = LoggerFactory.getLogger(Lec01LAzyStream.class);

    public static void main(String[] args) {

        //Example of Java Stream:
        //Stream.of(1)
        //.peek(i -> log.info("Received: " + i)); // This code will not run
        //Why won't it run?
        //Because there is no Terminal Operator (like forEach, collect, toArray).
        //A stream does nothing without a terminal operation.
        //peek() executes a side-effect for each item passed through the stream (like System.out.println() or log.info()).
        //But the important thing is that it does not execute the stream by itself. It only executes when a terminal operation (like collect(), forEach(), or count()) is attached to it.
        //
        //If we write it like this:
        //Stream.of(1)
        //.peek(i -> log.info("Received: " + i))
        //.collect(Collectors.toList());
        //Now the output is printed because .collect() is a terminal operator and "consumes" the stream.
        //Streams in Java and Publishers in Reactive Streams (like Mono and Flux) do not run without a subscriber.
        //Reactive Streams ≈ Lazy Java Streams + Asynchronous + Backpressure

        Stream.of(1)
                .peek(i -> logger.info("peek: " + i));

        Stream.of(1)
                .peek(i -> logger.info("received: " + i))
                .toList();

    }
}
