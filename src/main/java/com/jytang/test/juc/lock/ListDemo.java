package com.jytang.test.juc.lock;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 功能描述：
 *
 * @author jytang
 * @since 2021-08-22
 */
public class ListDemo {

    public static void main(String[] args) {
//        List list = new ArrayList();
//        List list = new Vector();
//        List list = Collections.synchronizedList(new ArrayList<>());
        List list = new CopyOnWriteArrayList();
        for (int i = 0; i < 30; i++) {
            new Thread(()->{
                list.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(list);
            },String.valueOf(i)).start();
        }
    }
}
