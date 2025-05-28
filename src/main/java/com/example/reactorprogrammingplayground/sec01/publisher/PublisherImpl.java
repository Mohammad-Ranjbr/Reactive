package com.example.reactorprogrammingplayground.sec01.publisher;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

public class PublisherImpl implements Publisher<String> {

    @Override
    public void subscribe(Subscriber<? super String> subscriber) {
        var subscription = new SubscriptionImpl(subscriber);
        subscriber.onSubscribe(subscription);
    }

    //1. Subscriber subscribes → Publisher.subscribe(subscriber)
    //The first step is to initiate a communication between the subscriber and the publisher.
    //The publisher creates a Subscription.
    //Then the Publisher calls the onSubscribe(subscription) method on the Subscriber.
    //2. Subscriber requests data → subscription.request(n)
    //Now the ball is in the Subscriber's court. It must explicitly state how much data it wants.
    //The number n means "I am ready to receive n items."
    //If this request is not made, no data is sent (this is where backpressure comes in).
    //3. Subscription sends data to the Subscriber → subscriber.onNext(item)
    //The Subscription is now required to send exactly n items.
    //After each item, onNext() is called on the Subscriber.
    //4. When the data is finished → subscriber.onComplete()
    //If the data list is finished (and no errors occurred), onComplete() is called.
    //5. If something goes wrong → subscriber.onError(error)
    //If any error occurs (e.g. invalid input, exception, or resources are lost), onError() is called and the flow is terminated.
    //6. At any time, the Subscriber can disconnect → subscription.cancel()
    //This method should prevent further data from being sent.
    //Typically, this method is called if the user no longer needs the data or is releasing resources.

    //Why do we pass the subscriber to SubscriptionImpl?
    //The reason is:
    //➤ Subscription is responsible for sending data to Subscriber
    //When Subscriber calls request(n) method, it is essentially telling Subscription:
    //I am ready to receive n items. Please send them.
    //And since SubscriptionImpl needs to send the data (via onNext(item)), it needs to have a reference to Subscriber itself so that it can:
    //subscriber.onNext(item)
    //subscriber.onComplete()
    //subscriber.onError(throwable)

}
