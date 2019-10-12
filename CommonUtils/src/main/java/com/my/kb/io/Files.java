package com.my.kb.io;

import java.io.InputStream;

public class Files {
    public static InputStream getInputStream(String path) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        return loader.getResourceAsStream(path);
    }
}
