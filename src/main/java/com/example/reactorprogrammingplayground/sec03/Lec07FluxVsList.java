package com.example.reactorprogrammingplayground.sec03;

import com.example.reactorprogrammingplayground.common.Util;
import com.example.reactorprogrammingplayground.sec03.helper.NameGenerator;

public class Lec07FluxVsList {
    public static void main(String[] args) {

        var list = NameGenerator.getNamesList(10);
        System.out.println(list);

        NameGenerator.getNamesFlux(10)
                .subscribe(Util.subscriber());

    }
}
