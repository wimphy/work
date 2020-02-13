package com.my.kb.aio;

import com.my.kb.net.AbstractClient;
import com.my.kb.net.IClient;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.*;

import static com.my.kb.utils.EasyLogger.log;


public class AsyncClient extends AbstractClient {
    private AsyncClient(String host, int port) {
        super(host, port);
    }

    public static void main(String[] args) {
        IClient client = new AsyncClient("127.0.0.1", 8080);
        client.run();
    }

    public AsynchronousSocketChannel connect() throws Exception {
        AsynchronousSocketChannel channel = AsynchronousSocketChannel.open();
        Future<Void> conFuture = channel.connect(new InetSocketAddress(getHost(), getPort()));
        conFuture.get();
        return channel;
    }

    @Override
    public void run() {
        try {
            AsynchronousSocketChannel channel = connect();
            ExecutorService executors = Executors.newFixedThreadPool(2);
            Future<?> fuRead = executors.submit(new AsyncClientRead(channel));
            Future<?> fuWrite = executors.submit(new AsyncClientWrite(channel));
            fuRead.get();
            fuWrite.get();
            executors.shutdown();
            executors.awaitTermination(1, TimeUnit.DAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
