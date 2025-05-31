package com.example.reactorprogrammingplayground.common;

import com.github.javafaker.Faker;
import org.reactivestreams.Subscriber;

public class Util {

    private static final Faker faker = Faker.instance();

    public static <T> Subscriber<T> subscriber(){
        return new DefaultSubscriber<>("");
    }

    public static <T> Subscriber<T> subscriber(String name){
        return new DefaultSubscriber<>(name);
    }

    public static Faker faker(){
        return faker;
    }

    public static void sleepSeconds(int seconds) throws InterruptedException {
        Thread.sleep(seconds * 1000L);
    }

}
