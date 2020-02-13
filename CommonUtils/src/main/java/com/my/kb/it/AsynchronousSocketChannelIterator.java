package com.my.kb.it;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.Future;

public class AsynchronousSocketChannelIterator extends ReadableByteChannelIterator {
    private AsynchronousSocketChannel channel;

    public AsynchronousSocketChannelIterator(AsynchronousSocketChannel channel) {
        this.channel = channel;
        this.buffer = ByteBuffer.allocate(MAX_LINE_LENGTH);
    }

    @Override
    protected int readToBuffer() throws Exception {
        Future<Integer> fu = channel.read(buffer);
        index = fu.get();
        return index;
    }
}
