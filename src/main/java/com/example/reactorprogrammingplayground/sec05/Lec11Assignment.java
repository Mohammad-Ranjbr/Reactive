package com.example.reactorprogrammingplayground.sec05;

import com.example.reactorprogrammingplayground.common.Util;
import com.example.reactorprogrammingplayground.sec05.client.ExternalServiceClient;

public class Lec11Assignment {
    public static void main(String[] args) throws InterruptedException {

        var client = new ExternalServiceClient();

        for(int i = 1; i < 5; i++){
            client.getProductName(i)
                    .subscribe(Util.subscriber());
        }

        Util.sleepSeconds(5);

    }
}
