package com.jytang.test.juc.lock;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

class MyTask extends RecursiveTask<Integer> {

    private static final Integer VALUE = 10;

    private int begin;

    private int end;

    private int result;

    public MyTask(int begin, int end) {
        this.begin = begin;
        this.end = end;
    }

    @Override
    protected Integer compute() {

        if (end - begin < VALUE) {
            for (int i = begin; i <= end; i++) {
                result = result + i;
            }
        } else {
            int middle = (end - begin) / 2;
            MyTask myTask1 = new MyTask(begin, middle);
            MyTask myTask2 = new MyTask(middle + 1, end);

            myTask1.fork();
            myTask2.fork();

            result = myTask1.join() + myTask2.join();

        }

        return result;
    }
}

/**
 * 功能描述：
 *
 * @author jytang
 * @since 2021-08-24
 */
public class ForkJoinDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MyTask myTask = new MyTask(0, 100);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Integer> submit = forkJoinPool.submit(myTask);
        Integer result = submit.get();
        System.out.println(result);
        forkJoinPool.shutdown();

    }

}
