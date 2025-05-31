package com.example.reactorprogrammingplayground.sec02;

import com.example.reactorprogrammingplayground.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.List;

public class Lec10MonoDefer {
    private static final Logger logger = LoggerFactory.getLogger(Lec10MonoDefer.class);
    public static void main(String[] args) throws InterruptedException {

        //Creating a Publisher is lightweight and fast, because only one object is created.
        //The heavy lifting is done inside the Supplier and only happens when you subscribe() it.
        //But suppose even creating a Publisher takes you time.
        //For example, because you have Thread.sleep(1) inside the method that returns the Publisher.
        //Even if you don't subscribe() , just calling that method will take you 1 second.
        //In that case, you can use Mono.defer() , which:
        //Takes a supplier from Mono (i.e., the method that creates Mono ).
        //What it does is postpone the creation of Mono itself until the moment you subscribe.

        //createPublisher();

//        Mono.defer(() -> {
//            try {
//                return createPublisher();
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        });

        Mono.defer(() -> {
            try {
                return createPublisher();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).subscribe(Util.subscriber("sub1"));

    }

    //time-consuming business logic
    private static int sum(List<Integer> list) throws InterruptedException {
        logger.info("finding the sum of {}", list);
        Util.sleepSeconds(3);
        return list.stream().mapToInt(Integer::intValue).sum();
    }

    private static Mono<Integer> createPublisher() throws InterruptedException {
        logger.info("creating publisher");
        var list = List.of(1, 2, 3);
        Util.sleepSeconds(1);  // Simulate heavy-duty operations
        return Mono.fromSupplier(() -> {
            try {
                return sum(list);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
