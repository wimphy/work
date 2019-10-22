package com.my.kb.aio;

import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.TimeUnit;

import static com.my.kb.utils.EasyLogger.log;

public class Server2 {
    public static void main(String[] args) {
        try {
            AsynchronousServerSocketChannel channel = AsynchronousServerSocketChannel.open();
            channel.bind(new InetSocketAddress(8080));

            channel.accept(null, new CompletionHandler<>() {
                @Override
                public void completed(AsynchronousSocketChannel result, Object attachment) {
                    log(result);
                }

                @Override
                public void failed(Throwable exc, Object attachment) {
                    log("failed: " + exc.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
