package com.my.kb.it;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.Iterator;

public class BytesLine implements Iterable<String> {
    private byte[] bytes = null;
    private ByteBuffer buffer = null;
    private InputStream inputStream = null;
    private ReadableByteChannel channel = null;
    private AsynchronousSocketChannel asyncChannel = null;

    public BytesLine(byte[] bytes) {
        this.bytes = bytes;
    }

    public BytesLine(ByteBuffer buffer) {
        this.buffer = buffer;
    }

    public BytesLine(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public BytesLine(ReadableByteChannel channel) {
        this.channel = channel;
    }

    public BytesLine(AsynchronousSocketChannel asyncChannel) {
        this.asyncChannel = asyncChannel;
    }

    @Override
    public Iterator<String> iterator() {
        if (buffer != null) {
            return new ByteBufferIterator(buffer);
        } else if (inputStream != null) {
            return new InputStreamIterator(inputStream);
        } else if (channel != null) {
            return new ReadableByteChannelIterator(channel);
        } else if (asyncChannel != null) {
            return new AsynchronousSocketChannelIterator(asyncChannel);
        }
        return new BytesIterator(bytes);
    }
}
