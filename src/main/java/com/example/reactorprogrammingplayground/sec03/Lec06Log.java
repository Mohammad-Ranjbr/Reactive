package com.example.reactorprogrammingplayground.sec03;

import com.example.reactorprogrammingplayground.common.Util;
import reactor.core.publisher.Flux;

public class Lec06Log {
    public static void main(String[] args) {

        //log() is an intermediate operator that sits between the Publisher and the Subscriber.
        //Its job is simply to log events that occur during the Reactive flow, such as:
        //Receive Subscription
        //Send Request
        //Receive Items (onNext)
        //Complete (onComplete)
        //Error (onError)
        //Why is log() useful?
        //In real systems, there may be dozens of intermediate operators between the Producer and the Subscriber.
        //If something goes wrong (such as not receiving data), it can be very difficult to figure out where the problem is.

        Flux.range(1, 5)
                .subscribe(Util.subscriber("sub1"));

        Flux.range(1, 5)
                .log()
                .subscribe(Util.subscriber("sub2"));

        //If you put log() before map, only the numbers 1, 2, 3 will be logged.
        //Because map changes the output, log doesn't know that map is converting the value to a name.
        //To see the names in the log, you need to put log() after map.
        Flux.range(1, 5)
                .log("between range and map")
                .map(i -> Util.faker().name().firstName())
                .log("map and subscribe")
                .subscribe(Util.subscriber("sub2"));

    }
}
