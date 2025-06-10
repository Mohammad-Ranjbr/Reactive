package com.example.reactorprogrammingplayground.sec05;

import com.example.reactorprogrammingplayground.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class Lec03DoCallbacks {
    private static final Logger log = LoggerFactory.getLogger(Lec03DoCallbacks.class);
    public static void main(String[] args) {

        //These are methods that allow you to perform operations (usually logging or side effects) at specific points in the reactive flow lifecycle.
        //These methods do not change the value (except doOnNext if you explicitly manipulate the value).
        //doFirst : Before anything else (runs from bottom to top) Best place for initial logging or preparation
        //doOnSubscribe : When sending a Subscription to downstream (logs from the start of the subscription)
        //doOnRequest : When downstream requests items (logs the number of items requested)
        //doOnNext : For each item that comes from upstream (logs or mutates the value)
        //doOnComplete : When producer signals complete (runs only once)
        //doOnError : When producer sends an error (displays an error)
        //doOnTerminate : On complete or error (always runs)
        //doOnCancel : When subscriber requests cancellation (only runs if canceled)
        //doOnDiscard : If the produced item is not received (e.g. due to take) you can check for missing items
        //doFinally : At the end of any state: complete, error, cancel (always runs last method)

        Flux.<Integer>create(fluxSink -> {
            log.info("produce begins");
            for(int i = 0; i <= 4; i++) {
                fluxSink.next(i);
            }
            fluxSink.complete();
//            fluxSink.error(new RuntimeException("oops"));
            log.info("produce ends");
        })
                .doOnComplete(() -> log.info("doOnComplete-1"))
                .doFirst(() -> log.info("doFirst-1"))
                .doOnNext(item -> log.info("doOnNext-1: {}", item))
                .doOnSubscribe(subscription -> log.info("doOnSubscribe-1: {}", subscription))
                .doOnRequest(request -> log.info("doOnRequest-1: {}", request))
                .doOnError(error -> log.info("doOnError-1: {}", error.getMessage()))
                .doOnTerminate(() -> log.info("doOnTerminate-1")) // complete or error case
                .doOnCancel(() -> log.info("doOnCancel-1"))
                .doOnDiscard(Object.class, o -> log.info("doOnDiscard-1: {}", o))
                .doFinally(signal -> log.info("doFinally-1: {}", signal)) // finally irrespective of the reason
                .take(20)
                .doOnComplete(() -> log.info("doOnComplete-2"))
                .doFirst(() -> log.info("doFirst-2"))
                .doOnNext(item -> log.info("doOnNext-2: {}", item))
                .doOnSubscribe(subscription -> log.info("doOnSubscribe-2: {}", subscription))
                .doOnRequest(request -> log.info("doOnRequest-2: {}", request))
                .doOnError(error -> log.info("doOnError-2: {}", error.getMessage()))
                .doOnTerminate(() -> log.info("doOnTerminate-2"))
                .doOnCancel(() -> log.info("doOnCancel-2"))
                .doOnDiscard(Object.class, o -> log.info("doOnDiscard-2: {}", o))
                .doFinally(signal -> log.info("doFinally-2: {}", signal))
                .take(30)
                .subscribe(Util.subscriber());


    }
}
