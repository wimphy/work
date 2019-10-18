package com.my.kb.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

import static com.my.kb.utils.EasyLogger.log;

public class Client implements Runnable {
    private String host;
    private int port;

    private boolean stop = false;
    private ByteBuffer buffer = ByteBuffer.allocate(20);
    private SocketChannel channel = null;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public void stop() {
        this.stop = true;
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
                channel.connect(new InetSocketAddress(host, port));
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
    public String toString() {
        return "Client{" +
                "host='" + host + '\'' +
                ", port=" + port +
                '}';
    }

    @Override
    public void run() {
        ping();
    }

    public static void main(String[] args) {
        Client client = new Client("127.0.0.1", 8080);
        client.ping();
    }
}
