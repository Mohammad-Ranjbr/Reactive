package com.example.reactorprogrammingplayground.sec05;

import com.example.reactorprogrammingplayground.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Lec06ErrorHandling {
    public static void main(String[] args) {

        onErrorContinue();

    }

    // when you want to return a hardcoded value / simple computation
    private static void onErrorReturn(){
        //If an error occurs during the execution of Flux or Mono (for example, division by zero or failure in a network request),
        //the flow is immediately stopped and onError is executed. Solution: Use onErrorReturn
        //If you put onErrorReturn before the map operator, the error will not be handled in the map
        //You can define multiple onErrorReturns for different types of exceptions. The first one that matches the exception type is executed.
        //onErrorReturn(value) returns value on any error
        //onErrorReturn(type, value) returns value only on the exception to the specified type
        Mono.just(5)
                .map(i -> i == 5 ? 5/0 : i)
                //.onErrorReturn(-1)
                .onErrorReturn(IllegalArgumentException.class, -1)
                .onErrorReturn(ArithmeticException.class, -2)
                .onErrorReturn(-3)
                .subscribe(Util.subscriber());
    }

    //Sometimes we don't want to return a constant value, but rather want to use another service in case of an error.
    //Solution: Use onErrorResume to return a new Mono or Flux in case of an error.
    private static Mono<Integer> fallback1(){
        return Mono.fromSupplier(() -> Util.faker().random().nextInt(10, 100));
    }

    private static Mono<Integer> fallback2(){
        return Mono.fromSupplier(() -> Util.faker().random().nextInt(100, 1000));
    }

    private static void onErrorResume1(){
        Mono.error(new ArithmeticException("oops"))
                .onErrorResume(ArithmeticException.class, ex -> fallback1())
                .onErrorResume(ex -> fallback2())
                .onErrorReturn(-5)
                .subscribe(Util.subscriber());
    }

    private static void onErrorResume2(){
        Mono.error(new RuntimeException("oops"))
                .onErrorResume(ArithmeticException.class, ex -> fallback1())
                .onErrorResume(ex -> fallback2())
                .onErrorReturn(-5)
                .subscribe(Util.subscriber());
    }

    private static void onErrorComplete(){
        //You don't want to:
        //Provide a fixed fallback value (onErrorReturn),
        //or use an alternative publisher (onErrorResume).
        //You want to: If an error occurs, instead of throwing an exception, just complete the stream (complete)
        //and let the subscriber think everything completed correctly, even if it didn't receive any data.
        //The solution: onErrorComplete()
        //This operator suppresses the error and instead sends the onComplete signal to the subscriber.
        Mono.error(new RuntimeException("oops"))
                .onErrorComplete()
                .subscribe(Util.subscriber());
    }

    private static void onErrorContinue(){
        //Default problem:
        //In reactive programming when an error occurs (onError): the entire flow stops and none of the subsequent elements are processed.
        //onErrorContinue(): This operator allows you to ignore the error and continue if one of the elements causes an error during processing.
        //Only applicable for Flux (because it has multiple elements)
        Flux.range(1, 10)
                .map(i -> i == 5 ? 5 / 0 : i)
                .onErrorContinue(
                        (ex, obj) -> System.out.println(ex + " -> " + obj)
                )
                .subscribe(Util.subscriber());
    }

}
