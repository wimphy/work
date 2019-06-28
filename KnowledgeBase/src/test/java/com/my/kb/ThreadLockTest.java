package com.my.kb;

import org.junit.Test;

import static com.my.kb.utils.MyLogger.log;

/*
synchronized
　　1）获取锁的线程执行完了该代码块，然后线程释放对锁的占有；
　　2）线程执行发生异常，此时JVM会让线程自动释放锁。
　　3) 读文件时，如果多个线程都只是进行读操作，所以当一个线程在进行读操作时，其他线程只能等待无法进行读操作。

Lock
　　1) 读文件时，多个线程都只是进行读操作时，线程之间不会发生冲突。
　　2) 通过Lock可以知道线程有没有成功获取到锁。这个是synchronized无法办到的。
　　3）Lock不是Java语言内置的，synchronized是Java语言的关键字，因此是内置特性。Lock是一个类，通过这个类可以实现同步访问；
　　4）Lock必须要用户去手动释放锁(发生异常不会释放锁)，如果没有主动释放锁，就有可能导致出现死锁现象。
* */
public class ThreadLockTest {
    @Test
    public void TestLock() throws InterruptedException {
        ClassWithLock classWithLock = new ClassWithLock();
        Thread t1 = new Thread(classWithLock::testLock);
        t1.start();
        Thread t2 = new Thread(classWithLock::testLock);
        t2.start();

        t1.join();
        t2.join();
    }

    @Test
    public void TestTryLock() throws InterruptedException {
        ClassWithLock classWithLock = new ClassWithLock();
        Thread t1 = new Thread(classWithLock::testLock);
        t1.start();
        Thread t2 = new Thread(classWithLock::testTryLock);
        t2.start();

        t1.join();
        t2.join();
    }

    @Test
    public void TestInterrupt() throws InterruptedException {
        ClassWithLock classWithLock = new ClassWithLock();
        Thread t1 = new Thread(classWithLock::testLock);
        t1.start();
        Thread t2 = new Thread(classWithLock::testInterrupt);
        t2.start();

        t1.join();
        t2.join();
        t2.interrupt();
    }

    @Test
    public void TestReadWriteLock() throws InterruptedException {
        ClassWithLock classWithLock = new ClassWithLock();
        Thread t1 = new Thread(classWithLock::testReadLock);
        t1.start();
        Thread t2 = new Thread(classWithLock::testWriteLock);
        t2.start();
        Thread t3 = new Thread(classWithLock::testReadLock);
        t3.start();
        Thread t4 = new Thread(classWithLock::testReadLock);
        t4.start();

        t1.join();
        t2.join();
        t3.join();
        t4.join();
    }

    @Test
    public void TestFairLock() throws InterruptedException {
        ClassWithLock classWithLock = new ClassWithLock();
        Thread t1 = new Thread(() -> classWithLock.testFairLock(1));
        t1.start();
        Thread t2 = new Thread(() -> classWithLock.testFairLock(2));
        t2.start();
        Thread t3 = new Thread(() -> classWithLock.testFairLock(3));
        t3.start();
        Thread t4 = new Thread(() -> classWithLock.testFairLock(4));
        t4.start();

        t1.join();
        t2.join();
        t3.join();
        t4.join();
    }

    @Test
    public void TestSyncClassFunc() throws InterruptedException {
        var r = new SyncClassFunc();
        var t1 = new Thread(r);
        var t2 = new Thread(r);
        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }
}
