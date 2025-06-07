package com.example.reactorprogrammingplayground.sec03;

import com.example.reactorprogrammingplayground.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Lec11FluxMono {
    public static void main(String[] args) {

        monoToFlux();

        //Mono can only send one message, so if Flux has multiple messages, we only need to choose one.
        //To do this, we can use flux.next() , which converts the first Flux message to a Mono.
        //Or we can use Mono.from(flux) , which behaves similarly to next() .
        //If Flux is empty and we use next() , Mono will also be empty.
        var flux = Flux.range(1, 10);
        flux.next()
                .subscribe(Util.subscriber());

        Mono.from(flux)
                .subscribe(Util.subscriber());

    }

    private static Mono<String> getUsername(int userId){
        return switch (userId){
          case 1 -> Mono.just("admin");
          case 2 -> Mono.empty();
          default -> Mono.error(new RuntimeException("Invalid input"));
        };
    }

    private static void save(Flux<String> flux) {
        flux.subscribe(Util.subscriber());
    }

    //Sometimes the library or code we use expects the data to be received as a Flux, but we have Mono.
    //The easiest way to do this is to use Flux.from(Mono).
    //This way the Mono is converted to a Flux.
    //Even if Mono is empty or an error, the corresponding Flux (empty or error) is created.
    private static void monoToFlux(){
        var mono = getUsername(1);
        save(Flux.from(mono));
    }

}
