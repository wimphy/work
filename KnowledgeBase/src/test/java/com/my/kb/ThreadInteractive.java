package com.my.kb;

import com.my.kb.yield.NormalThread;
import com.my.kb.yield.YieldThread;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.my.kb.utils.MyLogger.log;

public class ThreadInteractive {
    @Test
    public void testPool() throws InterruptedException {
        YieldThread t1 = new YieldThread();
        NormalThread t2 = new NormalThread();

        ExecutorService pool = Executors.newFixedThreadPool(2);
        pool.submit(t1);
        pool.submit(t2);

        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.MINUTES);

        /*
        * 365ms
        * 2019-06-28 16:35:37.526 pool-1-thread-2 com.my.kb.yield.NormalThread::run 999
        * 2019-06-28 16:35:37.538 pool-1-thread-1 com.my.kb.yield.YieldThread::run 58
        * 995ms
        * */
    }

    @Test
    public void testYield() {
        NormalThread t1 = new NormalThread();
        t1.start();

        for (int i = 0; i < 1000; i++) {
            log(i + "");
        }

        /*
        * 533ms
        * 2019-06-28 16:27:10.602 Thread-0 com.my.kb.yield.NormalThread::run 999
        * 2019-06-28 16:27:10.652 main com.my.kb.ThreadInteractive::testYield 473
        * 819ms
        * */
    }

    @Test
    public void testYield2() throws InterruptedException {
        YieldThread t1 = new YieldThread();
        t1.start();

        for (int i = 0; i < 1000; i++) {
            log(i + "");
        }
        t1.join();

        /*
        * 502ms
        * 2019-06-28 16:29:14.746 main com.my.kb.ThreadInteractive::testYield2 999
        * 2019-06-28 16:29:14.747 Thread-0 com.my.kb.yield.YieldThread::run 100
        * 843ms
        * */
    }

    @Test
    public void testYield3() {
        NormalThread t1 = new NormalThread();
        t1.start();

        for (int i = 0; i < 1000; i++) {
            Thread.yield();
            log(i + "");
        }

        /*
        * 542ms
        * 2019-06-28 16:30:33.782 Thread-0 com.my.kb.yield.NormalThread::run 999
        * 2019-06-28 16:30:33.790 main com.my.kb.ThreadInteractive::testYield3 48
        * 1018ms
        * */
    }
}
