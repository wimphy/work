package com.my.kb.aio;

import com.my.kb.net.AbstractServer;
import com.my.kb.net.IServer;

import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.Future;

import static com.my.kb.utils.SiLogger.log;

public class AsyncFutureServer extends AbstractServer {
    public AsyncFutureServer(int port) {
        super(port);
    }

    public static void main(String[] args) {
        IServer server = new AsyncFutureServer(8080);
        server.run();
    }

    @Override
    public void run() {
        try {
            AsynchronousServerSocketChannel channel = AsynchronousServerSocketChannel.open();
            channel.bind(new InetSocketAddress(getPort()));
            while (!stop) {
                Future<AsynchronousSocketChannel> fu = channel.accept();
                AsynchronousSocketChannel ch = fu.get();
                log(ch);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
