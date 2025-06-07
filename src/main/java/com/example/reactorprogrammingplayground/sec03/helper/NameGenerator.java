package com.example.reactorprogrammingplayground.sec03.helper;

import com.example.reactorprogrammingplayground.common.Util;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.IntStream;

public class NameGenerator {

    //Weaknesses of the traditional style:
    //All names are generated at once.
    //If each name takes one second to generate, and we want 10 names, we will be blocked for 10 seconds.
    //We do not see any output during execution, only the complete list comes at the end.
    //We cannot stop the generation in the middle.
    public static List<String> getNamesList(int count){
        return IntStream.rangeClosed(1, count)
                .mapToObj(i -> {
                    try {
                        return generateName();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
    }

    //Names are generated in a stream and step by step.
    //We have immediate output. For example, one name is printed every second.
    //We can stop the generation using Subscription.cancel().
    public static Flux<String> getNamesFlux(int count){
        return Flux.range(1, count)
                .map(i -> {
                    try {
                        return generateName();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    private static String generateName() throws InterruptedException {
        Util.sleepSeconds(1);
        return Util.faker().name().firstName();
    }

}
