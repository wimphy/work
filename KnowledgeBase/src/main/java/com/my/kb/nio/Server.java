package com.my.kb.nio;

import com.my.kb.io.Utils;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

import static com.my.kb.utils.EasyLogger.log;

public class Server implements Closeable, Runnable {
    private int port;
    private ServerSocket server;

    protected boolean stop = false;

    public Server(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public void start() throws IOException {
        this.server = new ServerSocket(port);
        stop = false;
        log("server stated: " + server);
        while (!stop) {
            Socket client = server.accept();
            SocketAddress address = client.getRemoteSocketAddress();
            log(address + " connected");
            try (InputStream inputStream = client.getInputStream()) {
                Utils.printString(inputStream);
            }
        }
    }

    @Override
    public void close() throws IOException {
        stop = true;
        server.close();
    }

    @Override
    public void run() {
        try {
            start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            Server ser = new Server(8080);
            ser.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
