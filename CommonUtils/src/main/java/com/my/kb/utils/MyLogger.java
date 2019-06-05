package com.my.kb.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyLogger {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public static void log(String msg) {
        var stack = Thread.currentThread().getStackTrace();
        var tName = Thread.currentThread().getName();
        Date date = new Date();
        String fMsg = String.format("%s %s %s::%s %s",
                sdf.format(date),
                tName,
                stack[2].getClassName(),
                stack[2].getMethodName(),
                msg);
        System.out.println(fMsg);
    }
}
