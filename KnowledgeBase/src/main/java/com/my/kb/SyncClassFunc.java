package com.my.kb;

import static com.my.kb.utils.MyLogger.log;

public class SyncClassFunc implements Runnable {
    @Override
    public void run() {
        try {
            log("running ...");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (SyncClassFunc.class) {
            show();
        }
    }

    public synchronized void show() {
        try {
            log("show started");
            Thread.sleep(2000);
            log("show ended");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
