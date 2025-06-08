package com.example.reactorprogrammingplayground.sec03.client;

import com.example.reactorprogrammingplayground.common.AbstractHttpClient;
import reactor.core.publisher.Flux;

public class ExternalServiceClient extends AbstractHttpClient {

    public Flux<String> getNames() {
        return this.httpClient.get()
                .uri("/demo02/name/stream")
                // Returns the response as a Flux<ByteBuf> (stream of bytes).
                .responseContent()
                // Converts any ByteBuf to String ⇒ Flux<String>.
                .asString();
    }

    public Flux<Integer> getPriceChanges() {
        return this.httpClient.get()
                .uri("/demo02/stock/stream")
                .responseContent()
                .asString()
                .map(Integer::parseInt);
    }

}
