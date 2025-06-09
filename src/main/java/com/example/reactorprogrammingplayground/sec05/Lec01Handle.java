package com.example.reactorprogrammingplayground.sec05;

import com.example.reactorprogrammingplayground.common.Util;
import reactor.core.publisher.Flux;

public class Lec01Handle {
    public static void main(String[] args) {

        //What do Operators do?
        //They add different behaviors to the data (filtering, transforming, aggregating, etc.).
        //They are located in the middle between the Publisher (the data producer) and the Subscriber (the data consumer).
        //They form the pipeline: data goes from the Publisher to the Subscriber, and along the way it can pass through several Operators.
        //Every time you use an Operator, a new Publisher is created.
        //So you should always subscribe to the last Publisher on which you applied the Operator chain.
        //In real applications, data usually does not stay in memory.
        //For example, data is taken from the UI, after applying business logic, it is stored in the database.
        //Or data comes from the database and after processing, it is sent to the UI.
        //All of these steps can be implemented using Operators.

        //handle() is a combination of two operations: filter() and map(). That is, you can decide whether to send a value or not (filter), and if you want, send a new value (map).
        //filter() can only delete.
        //map() can only modify the value.
        //But here we need a combination of deleting, modifying, and throwing an error all in one. That is exactly what handle() does for us.

        Flux.range(1, 10)
                //.filter(i -> i != 7)
                .handle((item, sink) -> {
                    switch (item) {
                        case 1 -> sink.next(-2);
                        case 4 -> {}
                        case 7 -> sink.error(new RuntimeException("oops"));
                        default -> sink.next(item);
                    }
                })
                //The output of handle() will be of type Flux<Object> because Java cannot determine the type accurately. To fix this, we use cast()
                .cast(Integer.class)
                .subscribe(Util.subscriber());

    }
}
