package com.my.kb.nio;

import com.my.kb.net.AbstractClient;
import com.my.kb.net.IClient;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static com.my.kb.utils.EasyLogger.log;
import static com.my.kb.utils.EasyLogger.logDate;

public class BlockClient extends AbstractClient {

    public BlockClient(String host, int port) {
        super(host, port);
    }

    @Override
    public void run() {
        while (!stop) {
            try {
                AsynchronousSocketChannel channel = AsynchronousSocketChannel.open();
                Future<Void> conFuture = channel.connect(new InetSocketAddress(getHost(), getPort()));
                conFuture.get();

                ByteBuffer buffer = ByteBuffer.allocate(30);
                Future<Integer> fu = channel.read(buffer);
                fu.get();
                logDate(buffer);

                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        IClient client = new BlockClient("127.0.0.1", 8080);
        client.run();
    }
}
