package com.my.kb.netty;

import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

import java.util.ArrayList;

public class FooHandler extends ChannelInitializer<SocketChannel> {
    private ArrayList<ChannelInboundHandlerAdapter> adapters = new ArrayList<>();

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        for (ChannelInboundHandlerAdapter adapter : adapters) {
            socketChannel.pipeline().addLast(adapter);
        }
    }

    public void addAdapter(ChannelInboundHandlerAdapter adapter) {
        this.adapters.add(adapter);
    }
}
