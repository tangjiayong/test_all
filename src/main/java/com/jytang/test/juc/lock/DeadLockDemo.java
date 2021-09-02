package com.jytang.test.juc.lock;

/**
 * 功能描述：
 *
 * @author jytang
 * @since 2021-08-22
 */
public class DeadLockDemo {

    public static void main(String[] args) {
        Object a = new Object();
        Object b = new Object();

        new Thread(() -> {
            synchronized (a) {
                System.out.println(Thread.currentThread().getName() + " 获取锁a，尝试获取锁b");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (b) {
                    System.out.println(Thread.currentThread().getName() + " 获取锁b");
                }
            }
        }, "A").start();

        new Thread(() -> {
            synchronized (b) {
                System.out.println(Thread.currentThread().getName() + " 获取锁b，尝试获取锁a");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (a) {
                    System.out.println(Thread.currentThread().getName() + " 获取锁a");
                }
            }
        }, "B").start();
    }
}
