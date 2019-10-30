package com.my.kb.netty;

import com.my.kb.net.IServer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class DiscardHandler extends ChannelInitializer<SocketChannel> {
    public void chRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf buf = (ByteBuf) msg;
        while (buf.isReadable()) {
            System.out.print((char) buf.readByte());
            System.out.flush();
        }

        System.out.println();
        ((ByteBuf) msg).release();
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter() {
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) {
                chRead(ctx, msg);
            }

            @Override
            public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
                // Close the connection when an exception is raised.
                cause.printStackTrace();
                ctx.close();
            }
        });
    }

    public static void main(String[] args) {
        IServer server = new FooNettyServer(8080);
        server.run();
    }
}
