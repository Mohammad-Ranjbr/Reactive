package com.example.reactorprogrammingplayground.sec02;

import com.example.reactorprogrammingplayground.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.List;

public class Lec06MonoFromSupplier {
    private static final Logger logger = LoggerFactory.getLogger(Lec06MonoFromSupplier.class);
    public static void main(String[] args) {

        var list = List.of(1, 2, 3);

        //The sum function is executed on the list right now, even if no one has subscribed yet.
        //This means that a heavy calculation (assuming the calculation is complex) is performed and may be wasted.
        Mono.just(sum(list));

        Mono.just(list)
                .subscribe(Util.subscriber("sub1"));

        //Here fromSupplier takes a Supplier that defers the execution of sum(list) until it is subscribed. That is, the sum calculation is only performed when someone mono.subscribe()s.
        Mono.fromSupplier(() -> sum(list));

        //Conclusion
        //If we have heavy operations, it is better to use fromSupplier to avoid wasting resources and processing unnecessarily.
        //just is only appropriate when the value is already available and ready (such as a fixed value or a value that is easy to calculate).
        Mono.fromSupplier(() -> list)
                .subscribe(Util.subscriber("sub2"));

    }

    private static int sum(List<Integer> list) {
        logger.info("finding the sum of {}", list);
        return list.stream().mapToInt(Integer::intValue).sum();
    }

}
