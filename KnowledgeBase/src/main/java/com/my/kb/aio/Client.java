package com.my.kb.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static com.my.kb.utils.EasyLogger.log;


public class Client {
    public static void main(String[] args) {
        try {
            while (true) {
                AsynchronousSocketChannel channel = AsynchronousSocketChannel.open();
                Future<Void> conFuture = channel.connect(new InetSocketAddress("127.0.0.1", 8080));
                conFuture.get();

                ByteBuffer buffer = ByteBuffer.wrap("I am async, I am alive".getBytes());
                Future<Integer> fu = channel.write(buffer);
                log("feature returned: " + fu.get());
                TimeUnit.SECONDS.sleep(1);
                channel.close();
            }

        } catch (IOException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
