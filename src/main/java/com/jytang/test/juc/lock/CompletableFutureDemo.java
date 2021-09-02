package com.jytang.test.juc.lock;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 功能描述：不知道哪里有问题
 *
 * @author jytang
 * @since 2021-08-24
 */
public class CompletableFutureDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        CompletableFuture<Void> completableFuture1 = CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().getName() + " completableFuture1");
        });
        completableFuture1.get();


        CompletableFuture<Integer> completableFuture2 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + " completableFuture2");

            return 1024;
        });

        completableFuture2.whenComplete((u, t) -> {
            System.out.println("u:" + u);
            System.out.println("t:" + t);
        }).get();
    }
}
