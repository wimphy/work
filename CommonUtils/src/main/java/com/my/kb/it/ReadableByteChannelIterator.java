package com.my.kb.it;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;

public class ReadableByteChannelIterator extends ByteBufferIterator {
    private ReadableByteChannel channel;
    private boolean more = true;

    public ReadableByteChannelIterator() {
    }

    public ReadableByteChannelIterator(ReadableByteChannel channel) {
        this.channel = channel;
        this.buffer = ByteBuffer.allocate(MAX_LINE_LENGTH);
    }

    @Override
    public boolean hasNext() {
        if (index == 0) {
            try {
                index = readToBuffer();
                more = buffer.position() > 0;
                buffer.flip();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return more;
    }

    @Override
    public String next() {
        try {
            while (more) {
                while (buffer.hasRemaining()) {
                    byte b = buffer.get();
                    String res = dealByte(b);
                    if (res != null) {
                        return res;
                    }
                }
                if (index == -1) {
                    more = false;
                    break;
                }
                buffer.clear();
                index = readToBuffer();
                buffer.flip();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return logAndGet();
    }

    protected int readToBuffer() throws Exception {
        return channel.read(buffer);
    }
}
