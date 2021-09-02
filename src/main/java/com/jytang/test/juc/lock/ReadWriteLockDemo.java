package com.jytang.test.juc.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 功能描述：
 *
 * @author jytang
 * @since 2021-08-23
 */
public class ReadWriteLockDemo {

    public static void main(String[] args) {


        ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        Lock readLock = readWriteLock.readLock();
        Lock writeLock = readWriteLock.writeLock();

        writeLock.lock();
        System.out.println("write lock");

        readLock.lock();
        System.out.println("read lock");




        readLock.unlock();
        writeLock.unlock();

    }
}
