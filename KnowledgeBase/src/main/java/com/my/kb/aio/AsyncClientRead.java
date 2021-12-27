package com.my.kb.aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static com.my.kb.utils.SiLogger.log;


public class AsyncClientRead implements Runnable {
    private AsynchronousSocketChannel channel;

    public AsyncClientRead(AsynchronousSocketChannel channel) {
        this.channel = channel;
    }

    @Override
    public void run() {
        try {
            while (channel != null && channel.isOpen()) {
                ByteBuffer buffer = ByteBuffer.allocate(30);
                Future<Integer> fu = channel.read(buffer);
                log("read: " + fu.get());
                log(buffer);
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
