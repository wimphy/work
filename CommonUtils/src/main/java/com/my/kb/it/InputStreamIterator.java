package com.my.kb.it;

import java.io.IOException;
import java.io.InputStream;

public class InputStreamIterator extends BytesIterator {
    private InputStream inputStream;

    public InputStreamIterator(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public boolean hasNext() {
        bytes = new byte[MAX_LINE_LENGTH];
        int more = -1;
        try {
            more = inputStream.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return more > -1;
    }
}
