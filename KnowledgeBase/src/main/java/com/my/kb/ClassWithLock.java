package com.my.kb;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static com.my.kb.utils.EasyLogger.log;

public class ClassWithLock {
    private Lock lock = new ReentrantLock();
    private Lock fairLock = new ReentrantLock(true);
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    /*
    Lock必须主动去释放锁，并且在发生异常时，不会自动释放锁，释放锁的操作放在finally块中进行，以保证锁一定被被释放，防止死锁的发生。
    * */
    public void testLock() {
        lock.lock();
        log("test started.");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            log("test ended.");
            lock.unlock();
        }
    }

    //tryLock
    public void testTryLock() {
        while (!lock.tryLock()) {
            log("obtain lock failed");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        lock.unlock();
        log("lock released");
    }

    public void testInterrupt() {
        try {
            lock.lockInterruptibly();
            log("lock obtained");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            log("thread interrupted failed");
        } finally {
            lock.unlock();
            log("lock released");
        }
    }

    public void testReadLock() {
        readWriteLock.readLock().lock();
        log("read lock");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            readWriteLock.readLock().unlock();
            log("read lock released");
        }
    }

    public void testWriteLock() {
        readWriteLock.writeLock().lock();
        log("write lock");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            readWriteLock.writeLock().unlock();
            log("write lock release");
        }
    }

    public void testFairLock(int s) {
        try {
            Thread.sleep(1000 * (Math.abs(2 - s)));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //fairLock.lock();
        lock.lock();
        log("test started.");
        try {
            Thread.sleep(1000 * s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            log("test ended.");
            //fairLock.unlock();
            lock.unlock();
        }
    }
}
