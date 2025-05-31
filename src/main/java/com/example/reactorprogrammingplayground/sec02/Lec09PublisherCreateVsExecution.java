package com.example.reactorprogrammingplayground.sec02;

import com.example.reactorprogrammingplayground.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class Lec09PublisherCreateVsExecution {
    private static final Logger logger = LoggerFactory.getLogger(Lec09PublisherCreateVsExecution.class);
    public static void main(String[] args) {

        //Because logger.info("entered the method"); is printed, it is unclear that it is not Lazy
        //Creating a Publisher ≠ Executing Business Logic
        //When we call a method like getName() and create Mono.fromSupplier(...) inside it, only a Mono object is created at that moment; no heavy operations are performed.
        //The time-consuming logic is inside the Supplier, not outside it
        //The code that really costs money (e.g., heavy computation or Thread.sleep(3)) is inside the λ that we give to fromSupplier.
        //This λ doesn’t run until someone subscribes() to Mono.
        //Why do some people think it’s not Lazy?
        //If you log outside the Supplier—for example, logger.info(“enter method”)—it will be printed immediately,
        //because the body of the getName() method (i.e., the “create Publisher” step) is executing.
        //But that’s just creating the Publisher; the heavy work hasn’t started yet.
        getName(); // Only the "enter method" log is printed

        getName() // Now λ is executed (wait 1 second)
                .subscribe(Util.subscriber("sub1"));

    }

    private static Mono<String> getName() {
        logger.info("entered the method");
        return Mono.fromSupplier(() ->{
            logger.info("generating name");
            try {
                Util.sleepSeconds(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return Util.faker().name().firstName();
        });
    }

}
