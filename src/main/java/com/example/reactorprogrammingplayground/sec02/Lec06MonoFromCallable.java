package com.example.reactorprogrammingplayground.sec02;

import com.example.reactorprogrammingplayground.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.List;

public class Lec06MonoFromCallable {
    private static final Logger logger = LoggerFactory.getLogger(Lec06MonoFromCallable.class);
    public static void main(String[] args) {

        //Difference:
        //If you have a method that may throw a checked exception, you cannot use it directly inside a Supplier because the get() function does not have a checked exception and the compiler will give an error.
        //But you can put it inside a Callable because its call() method can throw a checked exception.
        //In the Reactor project context:
        //Mono.fromSupplier(Supplier<T> supplier) is a method that takes a Supplier and since Supplier does not have a checked exception, it is good for simple tasks that do not have a checked exception.
        //Mono.fromCallable(Callable<T> callable) is a method that takes a Callable that can throw a checked exception and is more suitable for situations where our method may throw an Exception.

        var list = List.of(1, 2, 3);

        Mono.fromCallable(() -> sum(list))
                .subscribe(Util.subscriber("sub1"));

        Mono.fromSupplier(() -> {
                    try {
                        return sum(list);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .subscribe(Util.subscriber("sub2"));
    }

    private static int sum(List<Integer> list) throws Exception{
        logger.info("finding the sum of {}", list);
        return list.stream().mapToInt(Integer::intValue).sum();
    }

}
