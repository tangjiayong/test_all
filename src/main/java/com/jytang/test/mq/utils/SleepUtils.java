package com.jytang.test.mq.utils;

import java.util.concurrent.TimeUnit;

/**
 * 功能描述：
 *
 * @author jytang
 * @since 2021-08-28
 */
public class SleepUtils {

    public static void sleep(int second) {
        try {
            TimeUnit.SECONDS.sleep(second);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}
