package com.example.reactorprogrammingplayground.sec02;

import com.example.reactorprogrammingplayground.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class Lec07MonoFromRunnable {
    private static final Logger logger = LoggerFactory.getLogger(Lec07MonoFromRunnable.class);
    public static void main(String[] args) {

        //What is Mono.fromRunnable and when is it used?
        //Runnable is a functional interface in Java that has only one method: void run() with no input or output.
        //When we work with Mono.fromRunnable(), we have a piece of code (Runnable) that we want to run only when someone subscribes to this Mono.
        //This method causes lazy execution, like fromSupplier and fromCallable that you saw earlier.
        //Why should we use Mono.fromRunnable() instead of Mono.empty()?
        //Mono.empty() means that we have no data, we are only sending the completion signal.
        //But sometimes we want to do something before sending the completion signal (for example, log, notify the business, record data, etc.).
        //If we just send Mono.empty(), this extra code will not be executed because Mono only sends the completion signal.
        //So with Mono.fromRunnable() we can do something that first executes notifyBusiness(productId) and then sends a completion signal.
        //In Reactive Programming we always want things to be done only when someone subscribes, so that resources are not wasted and the program is optimized.
        //If we execute the notifyBusiness method right now but no one has subscribed to Mono, we are effectively using resources for no reason.
        //Mono.fromRunnable() helps to execute that method only when someone subscribes.
        getProductName(3)
                .subscribe(Util.subscriber("sub1"));

    }

    private static Mono<String> getProductName(int productId) {
        if(productId == 1){
            return Mono.fromSupplier(() -> Util.faker().commerce().productName());
        }
        return Mono.fromRunnable(() -> notifyBusiness(productId));
    }

    private static void notifyBusiness(int productId) {
        logger.info("notify business on unavailable product {}", productId);
    }

}
