package com.my.kb.yield;

import static com.my.kb.utils.EasyLogger.log;

public class NormalThread extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            log(i + "");
        }
    }
}
