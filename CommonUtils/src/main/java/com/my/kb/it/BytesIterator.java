package com.my.kb.it;

import java.io.ByteArrayOutputStream;
import java.util.Iterator;

public class BytesIterator implements Iterator<String> {
    protected final byte r = 0xff & '\r';
    protected final byte n = 0xff & '\n';
    protected final int MAX_LINE_LENGTH = 255;
    protected int index = 0;
    protected ByteArrayOutputStream lineBytes = new ByteArrayOutputStream();

    protected byte[] bytes;

    protected BytesIterator() {
    }

    public BytesIterator(byte[] bytes) {
        this.bytes = bytes;
    }

    @Override
    public boolean hasNext() {
        return index < bytes.length && bytes.length > 0;
    }

    @Override
    public String next() {
        for (byte b : bytes) {
            String res = dealByte(b);
            if (res != null) {
                return res;
            }
        }
        return logAndGet();
    }

    protected String dealByte(byte b) {
        String res = null;
        if (b != r && b != n) {
            lineBytes.writeBytes(new byte[]{b});
            if (index >= MAX_LINE_LENGTH) {
                res = logAndGet();
            }
        } else {
            res = logAndGet();
        }
        index++;
        return res;
    }

    protected String logAndGet() {
        String res = new String(lineBytes.toByteArray());
        lineBytes.reset();
        index = 0;
        return res;
    }
}
