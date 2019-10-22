package com.my.kb.aio;

import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static com.my.kb.utils.EasyLogger.log;

public class Server {
    public static void main(String[] args) {
        try {
            AsynchronousServerSocketChannel channel = AsynchronousServerSocketChannel.open();
            channel.bind(new InetSocketAddress(8080));
            while (true) {
                Future<AsynchronousSocketChannel> fu = channel.accept();
                AsynchronousSocketChannel ch = fu.get();
                log(ch);
            }
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
