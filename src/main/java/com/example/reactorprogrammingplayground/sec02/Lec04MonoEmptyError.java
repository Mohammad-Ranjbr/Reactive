package com.example.reactorprogrammingplayground.sec02;

import com.example.reactorprogrammingplayground.common.Util;
import reactor.core.publisher.Mono;

public class Lec04MonoEmptyError {
    public static void main(String[] args) {

        getUsername(1).subscribe(Util.subscriber("sub1"));
        // The onNext() method is not executed because there is no data (such as null), but since there is no error, only onComplete() is executed.
        getUsername(2).subscribe(Util.subscriber("sub2"));
        // Because an error occurred, only onError() is executed. Neither onNext() nor onComplete() are executed.
        getUsername(3).subscribe(Util.subscriber("sub3"));

    }

    //In reactive programming:
    //We never return null.
    //We use Mono.empty() for "no data but no error".
    //We use Mono.error(...) for "invalid input or serious problem".

    private static Mono<String> getUsername(int userId){
        return switch (userId){                         // Traditional
            case 1 -> Mono.just("Sam");    // return "Sam"
            case 2 -> Mono.empty();                      // return null
            default -> Mono.error(new IllegalArgumentException("Invalid user id"));  // throw new Exception
        };
    }

}
