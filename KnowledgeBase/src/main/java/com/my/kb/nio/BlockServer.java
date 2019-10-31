package com.my.kb.nio;

import com.my.kb.io.Utils;
import com.my.kb.net.AbstractServer;
import com.my.kb.net.IServer;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

import static com.my.kb.utils.EasyLogger.log;

public class BlockServer extends AbstractServer {
    private ServerSocket server;

    protected boolean stop = false;

    public BlockServer(int port) {
        super(port);
    }

    public void start() throws IOException {
        this.server = new ServerSocket(getPort());
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
    public void run() {
        try {
            start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        IServer ser = new BlockServer(8080);
        ser.run();
    }
}
