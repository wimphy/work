package com.my.kb.net;

public interface IServer extends Runnable {

    int getPort();

    void stop();
}
