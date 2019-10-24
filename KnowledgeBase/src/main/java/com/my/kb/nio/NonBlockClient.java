package com.my.kb.nio;

import com.my.kb.net.AbstractClient;
import com.my.kb.net.IClient;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

import static com.my.kb.utils.EasyLogger.log;

public class NonBlockClient extends AbstractClient {
    private ByteBuffer buffer = ByteBuffer.allocate(20);
    private SocketChannel channel = null;

    public NonBlockClient(String host, int port) {
        super(host,port);
    }
    private void connect() {
        while (!stop) {
            try {
                if (channel == null || !channel.isOpen()) {
                    log("trying to open channel ...");
                    channel = SocketChannel.open();
                    channel.configureBlocking(false);
                    log(channel + " channel opened");
                    continue;
                }
                if (channel.isConnected()) {
                    log("connected");
                    break;
                }
                if (channel.isConnectionPending()) {
                    log("pending connection");
                    if (channel.finishConnect()) {
                        log("connected");
                        break;
                    }
                }
                log("trying to connect to channel ...");
                channel.connect(new InetSocketAddress(getHost(), getPort()));
                if (channel.finishConnect()) {
                    log("connected");
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void ping() {
        while (!stop) {
            connect();
            try {
                buffer.clear();
                buffer.put("I am alive!".getBytes());
                buffer.flip();
                while (buffer.hasRemaining()) {
                    channel.write(buffer);
                }
                log(this.toString() + ": sent heart beat.");
            } catch (ClosedChannelException e) {
                e.printStackTrace();
                break;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (channel != null && channel.isOpen()) {
                    try {
                        channel.close();
                        System.out.println();
                        TimeUnit.SECONDS.sleep(2);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public void run() {
        ping();
    }

    public static void main(String[] args) {
        IClient client = new NonBlockClient("127.0.0.1", 8080);
        client.run();
    }
}
