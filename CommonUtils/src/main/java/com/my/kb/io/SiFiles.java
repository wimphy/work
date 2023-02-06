package com.my.kb.io;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.List;

public class SiFiles {
    public static InputStream getInputStream(String path) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        return loader.getResourceAsStream(path);
    }

    public static String readFromResource(String name) throws IOException {
        try (InputStream is = getInputStream(name)) {
            if (is == null) {
                return null;
            }
            return IOUtils.toString(is, Charset.defaultCharset());
        }
    }

    public static String read(String path) throws IOException {
        try (InputStream is = new FileInputStream(path)) {
            return IOUtils.toString(is, Charset.defaultCharset());
        }
    }
}
