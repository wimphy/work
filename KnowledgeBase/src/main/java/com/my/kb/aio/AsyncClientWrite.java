package com.my.kb.aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static com.my.kb.utils.SiLogger.log;


public class AsyncClientWrite implements Runnable {
    public AsynchronousSocketChannel channel;

    public AsyncClientWrite(AsynchronousSocketChannel channel) {
        this.channel = channel;
    }

    @Override
    public void run() {
        try {
            while (channel != null && channel.isOpen()) {
                ByteBuffer buffer = ByteBuffer.wrap("I am async, I am alive".getBytes());
                Future<Integer> fu = channel.write(buffer);
                log("feature returned: " + fu.get());
                TimeUnit.SECONDS.sleep(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
