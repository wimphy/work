package com.simon.tools.utils;

public class Common {
    public static void close(AutoCloseable foo) {
        if (foo != null)
            try {
                foo.close();
            } catch (Exception e) {
                //do nothing
            }
    }
}
