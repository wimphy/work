package com.my.kb.net;

public abstract class AbstractClient implements IClient {
    private String host;
    private int port;
    protected boolean stop = false;

    public AbstractClient(String host, int port) {
        this.port = port;
        this.host = host;
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public void stop() {
        stop = true;
    }

    @Override
    public String toString() {
        return "Client{" +
                "host='" + host + '\'' +
                ", port=" + port +
                '}';
    }
}
