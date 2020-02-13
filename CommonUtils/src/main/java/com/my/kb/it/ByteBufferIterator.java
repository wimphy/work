package com.my.kb.it;

import java.nio.ByteBuffer;

public class ByteBufferIterator extends BytesIterator {
    protected ByteBuffer buffer;

    public ByteBufferIterator() {
    }

    public ByteBufferIterator(ByteBuffer buffer) {
        this.buffer = buffer;
        this.buffer.flip();
    }

    @Override
    public boolean hasNext() {
        return buffer != null && buffer.hasRemaining();
    }

    @Override
    public String next() {
        do {
            byte b = buffer.get();
            String res = dealByte(b);
            if (res != null) {
                return res;
            }
        } while (buffer.hasRemaining());

        return logAndGet();
    }
}
