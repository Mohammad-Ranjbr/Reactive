package com.example.reactorprogrammingplayground.sec04;

import com.example.reactorprogrammingplayground.common.Util;
import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicInteger;

public class Lec08GenerateWithState {
    public static void main(String[] args) {

        demo2();

    }

    //When we use Flux.generate and want to have a stop condition, for example:
    //The flow stops when the country "Canada" is reached
    //Or when the number of emitted reaches 10
    //But the problem is that:
    //The lambda inside generate is executed without a new state each time and the state inside it is not preserved,
    //So if we define a variable like int counter=0 inside it, it will be zero every time,
    //If we try to counter++, the counter value will start from zero again with each execution,
    //So we cannot count the number of emitted correctly!

    private static void demo1() {
        //We can define an AtomicInteger outside generate
        //and incrementAndGet its value inside generate
        //but the problem is that this external state may be changed unintentionally by other parts of the code.
        AtomicInteger count = new AtomicInteger(0);
        Flux.generate(synchronousSink -> {
            var country = Util.faker().country().name();
            synchronousSink.next(country);
            count.incrementAndGet();
            if (count.get() == 10 || country.equalsIgnoreCase("canada")) {
                synchronousSink.complete();
            }
        }).subscribe(Util.subscriber());
    }

    private static void demo2() {
        //Here, the number 0 is defined as the initial state (counter).
        //This means that the first time generate is executed, our counter is 0.
        Flux.generate(
                () -> 0,
                (counter, sink) -> {
                    var country = Util.faker().country().name();
                    sink.next(country);
                    counter++;
                    if(counter == 10 || country.equalsIgnoreCase("canada")) {
                        sink.complete();
                    }
                    return counter; //You return this new counter value for the next execution to preserve the state.
                }).subscribe(Util.subscriber());
    }

}
