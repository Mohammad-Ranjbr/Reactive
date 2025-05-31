package com.example.reactorprogrammingplayground.sec02;

import com.example.reactorprogrammingplayground.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

public class Lec08MonoFromFuture {
    private final static Logger logger = LoggerFactory.getLogger(Lec08MonoFromFuture.class);
    public static void main(String[] args) throws InterruptedException {

        //.subscribe(Util.subscriber()) is a subscription to this Mono; that is, when executed, it expects to get the future result and perform an action on it (e.g. print).
        Mono.fromFuture(getName())
                .subscribe(Util.subscriber());
        Util.sleepSeconds(2); //The simple way is to pause the program for a while before main ends, so that async can finish its work.

        //Why isn't CompletableFuture lazy by default?
        //    //When you call CompletableFuture.supplyAsync(), it starts running in a separate thread right away.
        //    //That means the code inside supplyAsync starts running immediately.
        //    //But Reactive Programming (like Mono) is not like that, it is usually lazy; that is, the work is executed when someone subscribes.
        //getName() is called first.
        //getName() executes this line:
        //return CompletableFuture.supplyAsync(...);
        //This means that name generation starts immediately, even before subscribe() is executed.
        Mono.fromFuture(getName());

        Mono.fromFuture(Lec08MonoFromFuture::getName);

        //Solution: Using Supplier
        //makes getName lazy.
        //That is, getName() is not called until subscribe() is called.
        //So supplyAsync(...) is not called either.
        //Once subscribe() is done, getName() is called, and the async operation begins.
        Mono.fromFuture(Lec08MonoFromFuture::getName)
                .subscribe(Util.subscriber("sub1"));
        Util.sleepSeconds(2);
    }

    //What is CompletableFuture?
    //CompletableFuture is a class in Java for handling asynchronous operations.
    //It allows you to perform tasks in the future (after the program has run) and receive the result when it is ready.
    //You can chain and combine async operations.
    //In simple terms: instead of waiting for a long-running task to finish, the program executes that task in the background and returns the result later.
    //What does supplyAsync do?
    //CompletableFuture.supplyAsync(Supplier<U>) is a static method that executes a Supplier (a function that produces a value) asynchronously in a separate thread.
    //That is, when this method is called, the corresponding task is executed in the background and immediately returns a CompletableFuture whose result is prepared later.
    //In this example, () -> { logger.info("generating name"); return Util.faker().name().firstName(); } is a Supplier that generates a random name.

    //Because CompletableFuture.supplyAsync() does the work in a separate thread (the default thread pool is commonForkJoinPool).
    //But the main thread ends immediately and the application closes.
    //Before that asynchronous thread has a chance to return the name and send it to the subscriber.
    //That's why you only see the "generating name" log printed when the async task starts, but not the final value that should be received by the subscriber.

    private static CompletableFuture<String> getName() {
        return CompletableFuture.supplyAsync(() -> {
            logger.info("generating name");
            return Util.faker().name().firstName();
        });
    }

}
