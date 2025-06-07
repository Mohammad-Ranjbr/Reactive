package com.example.reactorprogrammingplayground.sec03;

import com.example.reactorprogrammingplayground.common.Util;
import com.example.reactorprogrammingplayground.sec03.client.ExternalServiceClient;

public class Lec08NonBlockingStreamingMessages {
    public static void main(String[] args) throws InterruptedException {

        var client = new ExternalServiceClient();
        client.getNames()
                .subscribe(Util.subscriber("Subscriber1"));

        client.getNames()
                .subscribe(Util.subscriber("Subscriber2"));

        Util.sleepSeconds(6);

    }
}
