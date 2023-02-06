package com.my.kb.utils;

import com.my.kb.it.BytesLine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.ReadableByteChannel;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

public class SiLogger {
    public final static int SAMPLE_ARRAY_COUNT = 10;
    private static final Logger logger = LogManager.getLogger(SiLogger.class);

    public static void log(String msg) {
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

    public static void log(AsynchronousSocketChannel channel) {
        BytesLine lines = new BytesLine(channel);
        for (String line : lines) {
            log(line);
        }
    }

    public static void log(int[] arr) {
        if (arr.length <= SAMPLE_ARRAY_COUNT) {
            log(Arrays.toString(arr));
            return;
        }
        Random r = new Random();
        int[] samples = new int[SAMPLE_ARRAY_COUNT];
        int segment = arr.length / SAMPLE_ARRAY_COUNT;
        for (int i = 0; i < SAMPLE_ARRAY_COUNT; i++) {
            int pos = r.nextInt(segment);
            pos = i * segment + pos;
            samples[i] = arr[pos];
        }
        log(String.format("Samples (%d of %d) %s",
                SAMPLE_ARRAY_COUNT, arr.length, Arrays.toString(samples)));
    }

    public static void logDate(ByteBuffer buffer) {
        BytesLine lines = new BytesLine(buffer);
        for (String line : lines) {
            if (line.length() == 13) {
                long l = Long.parseLong(line);
                Date dt = new Date(l);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                log(simpleDateFormat.format(dt));
            } else {
                log(line);
            }
        }
    }
}