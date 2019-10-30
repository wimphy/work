package com.my.kb.netty;

import io.netty.channel.ChannelHandlerContext;

public class EchoHandler extends DiscardHandler {
    @Override
    public void chRead(ChannelHandlerContext ctx, Object msg) {
        ctx.write(msg);
        ctx.flush();
    }

    public static void main(String[] args) {
        FooNettyServer server = new FooNettyServer(8080);
        server.setHandler(new EchoHandler());
        server.run();
    }
}
