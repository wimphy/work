package com.my.kb.net;

public interface IClient extends Runnable {

    int getPort();

    String getHost();

    void stop();
}
