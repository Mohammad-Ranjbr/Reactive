package com.example.reactorprogrammingplayground.sec04;

import com.example.reactorprogrammingplayground.common.Util;
import com.example.reactorprogrammingplayground.sec04.helper.NameGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.ArrayList;

public class Lec03FluxSinkThreadSafety {
    private static final Logger logger = LoggerFactory.getLogger(Lec03FluxSinkThreadSafety.class);
    public static void main(String[] args) throws InterruptedException {

        demo1();

    }

    //ArrayList is not thread-safe by default.
    //When multiple threads perform add() operations on it simultaneously, it is possible to:
    //Data is lost.
    //Eventually the number of items becomes less than 10,000.
    //Here, we use FluxSink<String> which is initialized in Flux.create(...).
    //generator.generate() sends values to Flux via sink.next(...).
    //These values are then received sequentially by the subscriber and stored in the list.
    //Even if this generate() is called from multiple threads, because FluxSink is internally thread-safe:
    //No data is lost.
    //All 10,000 items are received and stored.

    //What does Thread-Safe mean?
    //Suppose you have a notebook, and several people want to write something in it at the same time.
    //If everyone writes together and no one is waiting, the writing may get mixed up or erased or misplaced.
    //But if they write one by one (taking turns), everything remains neat.
    //Here's an example:
    //When several Threads (i.e., several simultaneous executions of the program) work with a common resource like a list, if that resource is not thread-safe, the data will be corrupted or incomplete.
    //ArrayList is not thread-safe
    //That is, when several Threads write data to it at the same time, some of the data may be lost or not saved correctly.
    //But some things are thread-safe (like FluxSink)
    //That is, they are designed in such a way that several Threads can work with it at the same time, without corrupting the data.
    //Runnable is a task or task.
    //That is, you say: "This is what I want to do inside a Thread".
    //You define a task (inside a Runnable)
    //You give it to a Thread to execute.

    private static void demo1() throws InterruptedException {
        var list = new ArrayList<Integer>();
        Runnable runnable = () -> {
          for (var i = 0; i < 1000; i++) {
              list.add(i);
          }
        };
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(runnable);
            thread.start();
        }
        Util.sleepSeconds(5);
        logger.info("list size (arraylist is not thread safe): {}", list.size());
    }

    private static void demo2() throws InterruptedException {
        var list = new ArrayList<String>();
        var generator = new NameGenerator();
        var flux = Flux.create(generator);
        flux.subscribe(list::add);

        Runnable runnable = () -> {
            for (var i = 0; i < 1000; i++) {
                generator.generate();
            }
        };
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(runnable);
            thread.start();
        }
        Util.sleepSeconds(5);
        logger.info("list size (flux sink is thread safe): {}", list.size());
    }

}
