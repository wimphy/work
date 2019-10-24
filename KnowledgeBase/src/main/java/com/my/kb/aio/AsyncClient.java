package com.my.kb.aio;

import com.my.kb.net.AbstractClient;
import com.my.kb.net.IClient;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static com.my.kb.utils.EasyLogger.log;


public class AsyncClient extends AbstractClient {
    private AsyncClient(String host, int port) {
        super(host, port);
    }

    public static void main(String[] args) {
        IClient client = new AsyncClient("127.0.0.1", 8080);
        client.run();
    }

    @Override
    public void run() {
        try {
            while (!stop) {
                AsynchronousSocketChannel channel = AsynchronousSocketChannel.open();
                Future<Void> conFuture = channel.connect(new InetSocketAddress(getHost(), getPort()));
                conFuture.get();

                ByteBuffer buffer = ByteBuffer.wrap("I am async, I am alive".getBytes());
                Future<Integer> fu = channel.write(buffer);
                log("feature returned: " + fu.get());
                TimeUnit.SECONDS.sleep(1);
                channel.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
