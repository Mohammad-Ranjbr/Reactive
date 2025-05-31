package com.example.reactorprogrammingplayground.sec02;

import com.example.reactorprogrammingplayground.sec01.subscriber.SubscriberImpl;
import reactor.core.publisher.Mono;

public class Lec02MonoJust {
    public static void main(String[] args) {

        Mono<String> mono = Mono.just("Hello");
        var subscriber = new SubscriberImpl();
        mono.subscribe(subscriber);
        subscriber.getSubscription().request(10);

        //What is Mono?
        //Mono<T> is one of the two main types of publishers in Project Reactor.
        //A Mono is a publisher that either publishes a value or nothing (empty).
        //Just like Java streams, which are lazy, Mono does not publish anything until it is subscribed to.
        //
        //Mono.just() is a factory method that quickly creates a Mono from any data type.
        //The data type passed to just() determines the type of Mono:
        //Mono.just("hello") → Mono<String>
        //Mono.just(10) → Mono<Integer>
        //Without request(), no data is sent.
        //So Mono.just() is a quick and convenient tool for:
        //Converting a simple value to a publisher
        //Use where reactive libraries expect a publisher
        //
        //The phrase Factory Method is a design pattern in object-oriented programming that is used to create Objects without directly referring to a concrete class.
        //Simple definition: What is a Factory Method?
        //A static method that returns an object of a specific class, without us having to use new directly.
        //public class User {
        //private String name;
        //
        //private User(String name) {
        //this.name = name;
        //}
        //
        //public static User of(String name) {
        //return new User(name);
        //}
        //
        //}
        //just() is a factory method that allows us to create a Mono in a simpler and cleaner way instead of using new MonoJust<>("hello").

    }
}
