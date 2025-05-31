package com.example.reactorprogrammingplayground.sec01;

import com.example.reactorprogrammingplayground.sec01.publisher.PublisherImpl;
import com.example.reactorprogrammingplayground.sec01.subscriber.SubscriberImpl;

public class Demo {
    public static void main(String[] args) throws InterruptedException {

        demo4();

    }

    private static void demo1(){
        // Publisher is lazy: No items are generated until request(n) is made. This is the principle of "pull-oriented" data flow in Reactive Streams.
        var publisher = new PublisherImpl();
        var subscriber = new SubscriberImpl();
        publisher.subscribe(subscriber);
    }

    private static void demo2() throws InterruptedException {
        //The Publisher sends exactly n or fewer items, and onNext() is called each time.
        //We can request a “batch”—the consumer says “give me 3 now, then 3 more later…”.
        //When the data runs out (count == MAX_ITEMS), the Publisher should call onComplete() once and then not send anything else.
        var publisher = new PublisherImpl();
        var subscriber = new SubscriberImpl();
        publisher.subscribe(subscriber);
        subscriber.getSubscription().request(3);
        Thread.sleep(2000);
        subscriber.getSubscription().request(3);
        Thread.sleep(2000);
        subscriber.getSubscription().request(3);
        Thread.sleep(2000);
        subscriber.getSubscription().request(3);
        Thread.sleep(2000);
        subscriber.getSubscription().request(3);
        Thread.sleep(2000);
    }

    private static void demo3() throws InterruptedException {
        //Whenever a Subscriber cancels(), it should:
        //set the isCancelled flag to true.
        //Following requests (request()) are aborted.
        //This shows how the "Backpressure + cancellation" process is handled.
        var publisher = new PublisherImpl();
        var subscriber = new SubscriberImpl();
        publisher.subscribe(subscriber);
        subscriber.getSubscription().request(3);
        Thread.sleep(2000);
        subscriber.getSubscription().cancel();
        subscriber.getSubscription().request(3);

    }

    private static void demo4() throws InterruptedException {
        //If the validation condition is violated (e.g. request(11) when we only have 10 items), onError() should be called immediately.
        //No more items should be sent after onError()—we should terminate the connection with the equivalent of cancel (isCancelled = true).
        var publisher = new PublisherImpl();
        var subscriber = new SubscriberImpl();
        publisher.subscribe(subscriber);
        subscriber.getSubscription().request(3);
        Thread.sleep(2000);
        subscriber.getSubscription().request(11);
        Thread.sleep(2000);
        subscriber.getSubscription().request(3);
        Thread.sleep(2000);
    }

}
