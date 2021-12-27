package com.my.kb.aio;

import com.my.kb.net.AbstractServer;
import com.my.kb.net.IServer;

import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

import static com.my.kb.utils.SiLogger.log;

public class AsyncHandlerServer extends AbstractServer {
    public AsyncHandlerServer(int port) {
        super(port);
    }

    public static void main(String[] args) {
        IServer server = new AsyncHandlerServer(8080);
        Thread t = new Thread(server);
        t.start();
        try {
            t.join();
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            AsynchronousServerSocketChannel channel = AsynchronousServerSocketChannel.open();
            channel.bind(new InetSocketAddress(getPort()));

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
    }
}
