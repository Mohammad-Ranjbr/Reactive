package com.example.reactorprogrammingplayground.sec04;

import com.example.reactorprogrammingplayground.common.Util;
import com.example.reactorprogrammingplayground.sec01.subscriber.SubscriberImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class Lec04FluxCreateDownStreamDemand {
    private static final Logger logger = LoggerFactory.getLogger(Lec04FluxCreateDownStreamDemand.class);
    public static void main(String[] args) throws InterruptedException {

        //When you use Flux.create() and generate data inside it, for example with sink.next(...), that data is generated immediately, regardless of the subscriber's need or request.
        //Even if the subscriber has not made any requests (i.e., does not request() ), all data is still generated immediately and placed in a queue.
        //Sometimes it's good:
        //For example, when data generation is very expensive or everything needs to be ready from the beginning
        //Sometimes it's bad:
        //If data generation is heavy, memory consumption is high, and the subscriber may not want it at all
        //Producer → sink.next(...): Data is produced and added to the queue
        //Subscriber → request(n): n items are removed from the queue and delivered
        //If cancel() is called: all data in the queue is discarded
        //The queue inside FluxSink is unbounded by default (A queue is a memory that holds data for a subscriber to read.)
        //That is, it can hold up to Integer.MAX_VALUE items.
        //If the data is very heavy or the number of items increases and the subscriber does not consume it →‌ it may go OutOfMemory!

        //Upstream = Data Producer (Publisher)
        //Downstream = Data Consumer (Subscriber)
        //"Downstream Demand" means:
        //The amount of data that the consumer (Subscriber) requests from the producer (Publisher).

        var subscriber = new SubscriberImpl();
        Flux.<String>create(fluxSink -> {
            for (var i = 0; i < 10; i++) {
                var name = Util.faker().name().firstName();
                logger.info("generated: {}", name);
                fluxSink.next(name);
            }
            fluxSink.complete();
        }).subscribe(subscriber);

        Util.sleepSeconds(2);
        subscriber.getSubscription().request(2);
        Util.sleepSeconds(2);
        subscriber.getSubscription().request(2);
        Util.sleepSeconds(2);
        subscriber.getSubscription().cancel();

    }
}
