package com.example.reactorprogrammingplayground.sec03;

import com.example.reactorprogrammingplayground.common.Util;
import com.example.reactorprogrammingplayground.sec03.client.ExternalServiceClient;
import com.example.reactorprogrammingplayground.sec03.helper.StockPriceObserver;

public class Lec12Assignment {
    public static void main(String[] args) throws InterruptedException {

        var client = new ExternalServiceClient();
        var subscriber = new StockPriceObserver();
        client.getPriceChanges()
                .subscribe(subscriber);

        Util.sleepSeconds(20);

    }
}
