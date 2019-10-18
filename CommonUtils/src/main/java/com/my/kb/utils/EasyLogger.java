package com.my.kb.utils;

import com.my.kb.it.BytesLine;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.util.logging.Logger;

public class EasyLogger extends Logger {
    protected EasyLogger(String name, String resourceBundleName) {
        super(name, resourceBundleName);
    }

    public static void log(String msg) {
        var stack = Thread.currentThread().getStackTrace();
        String name = stack[2].getClassName();
        Logger logger = Logger.getLogger(name);
        logger.info(msg);
    }

    public static void log(ByteBuffer buffer) {
        BytesLine lines = new BytesLine(buffer);
        for (String line : lines) {
            log(line);
        }
    }

    public static void log(InputStream inputStream) {
        BytesLine lines = new BytesLine(inputStream);
        for (String line : lines) {
            log(line);
        }
    }

    public static void log(byte[] buffer) {
        BytesLine lines = new BytesLine(buffer);
        for (String line : lines) {
            log(line);
        }
    }

    public static void log(ReadableByteChannel channel) {
        BytesLine lines = new BytesLine(channel);
        for (String line : lines) {
            log(line);
        }
    }
}
