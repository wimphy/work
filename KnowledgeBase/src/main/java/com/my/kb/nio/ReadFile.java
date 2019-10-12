package com.my.kb.nio;

import com.my.kb.io.Files;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

import static com.my.kb.utils.MyLogger.log;

public class ReadFile {
    private final byte r = "\r".getBytes()[0];
    private final byte n = "\n".getBytes()[0];

    private ArrayList<Byte> bytesLine = new ArrayList<>();

    public void bioRead() {
        InputStream inputStream = Files.getInputStream("file2.txt");
        byte[] buffer = new byte[10];
        try {
            int more;
            do {
                more = inputStream.read(buffer);
                log(more == -1 ? "done" : more + " bytes left");
                printLine(buffer);
            } while (more != -1);
            if (bytesLine.size() > 0) {
                dealByte(r);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void nioRead() {
        try {
            RandomAccessFile file = new RandomAccessFile("src/main/resources/file.txt", "r");
            FileChannel channel = file.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(10);
            int more;
            do {
                more = channel.read(buffer);
                log(more == -1 ? "done" : more + " bytes read");
                printLine(buffer);
            } while (more != -1);
            if (bytesLine.size() > 0) {
                dealByte(r);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printLine(byte[] buffer) {
        for (byte b : buffer) {
            dealByte(b);
        }
    }

    private void printLine(ByteBuffer buffer) {
        buffer.flip();
        while (buffer.hasRemaining()) {
            byte b = buffer.get();
            dealByte(b);
        }
        buffer.clear();
    }

    private void dealByte(byte b) {
        if (b != r && b != n && b != 0) {
            bytesLine.add(b);
            return;
        }
        if (bytesLine.size() == 0) {
            return;
        }
        String s = new String(getLineBytes());
        bytesLine.clear();
        log(s);
    }

    private byte[] getLineBytes() {
        byte[] bytes = new byte[bytesLine.size()];
        for (int i = 0; i < bytesLine.size(); i++) {
            bytes[i] = bytesLine.get(i);
        }
        return bytes;
    }
}
