package com.my.kb.yield;

import static com.my.kb.utils.MyLogger.log;

public class YieldThread extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            Thread.yield();
            log(i + "");
        }
    }
}
