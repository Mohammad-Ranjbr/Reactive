package com.example.reactorprogrammingplayground.sec02;

import com.example.reactorprogrammingplayground.common.Util;
import com.example.reactorprogrammingplayground.sec02.client.ExternalServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec11NonBlockingIO {
    private static final Logger logger = LoggerFactory.getLogger(Lec11NonBlockingIO.class);
    public static void main(String[] args) throws InterruptedException {

        //When we subscribe to Mono, an HTTP request is sent to the server and the response is received non-blocking.
        //Because Mono is non-blocking, main finishes quickly without waiting for a response, and we don't see the result.
        //For testing in the console application, we set Thread.sleep(2000) to wait 2 seconds (because the API response has a one-second delay).
        //Why shouldn't we Thread.sleep in the real world?
        //Because real applications (e.g. with Spring WebFlux) run continuously ,and we don't have to worry about the program ending.
        //Thread.sleep is only for the purpose of seeing the responses in the sample and console application.

        logger.info("Starting...");
        var client = new ExternalServiceClient();
        client.getProductName(1)
                .subscribe(Util.subscriber());

        //Sending multiple requests simultaneously with a for loop
        //We made a loop from 1 to 5 and sent 5 requests simultaneously (simultaneously, i.e. non-blocking) and subscribed.
        //Result: All 5 responses arrived after about a second, but the order of responses may be different because
        //The requests were sent simultaneously
        //The time to receive the response depends on network latency and server processing (so the order of responses is not necessarily the same as the order of sending).
        //Important point about threads and efficiency
        //All this work was done with just one thread!
        //If we wanted to do the same thing in a traditional and blocking way:
        //We would have to send 5 requests one by one and each would take 1 second
        //Or we would have asked for 5 separate threads
        //But here in one second with one thread, all 5 requests were done.
        //When we send 50 or 100 requests, the whole thing still takes about 1 second and only one thread is working.
        //In blocking mode, 100 requests = at least 100 seconds or 100 threads were needed.

        for (int i=1; i <= 100; i++){
            client.getProductName(i)
                    .subscribe(Util.subscriber());
        }
        Util.sleepSeconds(2);

    }
}
