package org.tretton63;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {

        try ( var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            IntStream.range(0, 10_000).forEach(i -> {
                executor.submit(() -> {
                    try {
                        Thread.sleep(Duration.ofSeconds(2));
                        System.out.printf("%d\n", i);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return i;
                });
            });
        }
        System.out.println("Hello world!");

    }
}