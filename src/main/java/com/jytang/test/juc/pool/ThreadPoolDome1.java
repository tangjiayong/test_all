package com.jytang.test.juc.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 功能描述：
 *
 * @author jytang
 * @since 2021-08-23
 */
public class ThreadPoolDome1 {

    public static void main(String[] args) {


        ExecutorService executorService1 = Executors.newFixedThreadPool(5);
        try {
            for (int i = 0; i < 10; i++) {
                executorService1.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "办理业务");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService1.shutdown();
        }

        ExecutorService executorService2 = Executors.newCachedThreadPool();
        try {
            for (int i = 0; i < 20; i++) {
                executorService2.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "办理业务");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService2.shutdown();
        }


    }
}
