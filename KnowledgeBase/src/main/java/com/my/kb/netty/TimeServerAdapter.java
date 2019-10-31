package com.my.kb.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimeServerAdapter extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        long t = System.currentTimeMillis();
        ByteBuf time = ctx.alloc().buffer(8);
        time.writeBytes(("" + t).getBytes());
        ctx.writeAndFlush(time);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    public static void main(String[] args) {
        TimeServerAdapter adapter = new TimeServerAdapter();
        FooNettyServer server = new FooNettyServer(8080);
        server.addAdapter(adapter);
        server.run();
    }
}
