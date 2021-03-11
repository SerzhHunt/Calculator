package com.epam.learn;

import com.epam.learn.calculator.console.Console;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(new Console());
        service.shutdown();
    }
}
