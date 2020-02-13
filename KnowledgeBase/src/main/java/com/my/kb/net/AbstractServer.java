package com.my.kb.net;

public abstract class AbstractServer implements IServer {
    private int port;
    protected boolean stop = false;

    public AbstractServer(int port) {
        this.port = port;
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public void stop() {
        stop = true;
    }

    @Override
    public String toString() {
        return "Server{" +
                "port=" + port +
                '}';
    }
}
