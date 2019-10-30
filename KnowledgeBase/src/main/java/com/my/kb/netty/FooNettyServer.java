package com.my.kb.netty;

import com.my.kb.net.AbstractServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class FooNettyServer extends AbstractServer {
    private ChannelHandler handler = null;

    public FooNettyServer(int port) {
        super(port);
    }

    public void setHandler(ChannelHandler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        EventLoopGroup parentGrp = new NioEventLoopGroup();
        EventLoopGroup childGrp = new NioEventLoopGroup();
        ServerBootstrap server = new ServerBootstrap();
        if (handler == null) {
            handler = new DiscardHandler();
        }
        try {
            server.group(parentGrp, childGrp)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(handler)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture fu = server.bind(getPort()).sync();
            fu.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            parentGrp.shutdownGracefully();
            childGrp.shutdownGracefully();
        }
    }
}
