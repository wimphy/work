package com.my.kb.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static com.my.kb.utils.SiLogger.log;

public class NonBlockServer extends BlockServer {
    public NonBlockServer(int port) {
        super(port);
    }

    @Override
    public void start() throws IOException {
        log("starting server ...");
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        serverChannel.bind(new InetSocketAddress(getPort()));
        log(serverChannel.toString());
        Selector selector = Selector.open();
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (!stop) {
            int cnt = selector.select();
            if (cnt == 0) {
                log("no ready events ...");
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> iter = selectedKeys.iterator();
            while (iter.hasNext()) {
                SelectionKey key = iter.next();
                switch (key.readyOps()) {
                    case SelectionKey.OP_CONNECT://key.isConnectable()
                        handleConnect(key);
                        break;
                    case SelectionKey.OP_ACCEPT:
                        handleAccept(key);
                        break;
                    case SelectionKey.OP_READ:
                        handleRead(key);
                        break;
                    case SelectionKey.OP_WRITE:
                        handleWrite(key);
                        break;
                }
                iter.remove();
            }
        }
    }

    private void handleConnect(SelectionKey key) {
        log("connected");
    }

    private void handleAccept(SelectionKey key) throws IOException {
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        SocketChannel clientChannel = serverChannel.accept();
        if (clientChannel == null) {
            log("not accept");
            return;
        }
        log(clientChannel + " accept");
        clientChannel.configureBlocking(false);
        clientChannel.register(key.selector(), SelectionKey.OP_READ);
    }

    private void handleRead(SelectionKey key) {
        SocketChannel channel = (SocketChannel) key.channel();
        log(channel);
        try {
            channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleWrite(SelectionKey key) {
        Set<String> set = new HashSet<>();
        log("write");
    }

    public static void main(String[] args) {
        NonBlockServer ser = new NonBlockServer(8080);
        try {
            ser.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
