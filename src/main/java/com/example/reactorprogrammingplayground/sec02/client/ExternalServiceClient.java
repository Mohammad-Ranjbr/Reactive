package com.example.reactorprogrammingplayground.sec02.client;

import com.example.reactorprogrammingplayground.common.AbstractHttpClient;
import reactor.core.publisher.Mono;

public class ExternalServiceClient extends AbstractHttpClient {

    public Mono<String> getProductName(int productId) {
        return this.httpClient.get()
                .uri("/demo01/product/" + productId)
                // Returns the response as a Flux<ByteBuf> (stream of bytes).
                .responseContent()
                // Converts any ByteBuf to String ⇒ Flux<String>.
                .asString()
                //Since we expect only one response, we get the first (and only) Flux item with next() ⇒ Mono<String>.
                .next();
    }

}
