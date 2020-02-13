package com.my.kb.io;

import java.io.*;

import static com.my.kb.utils.EasyLogger.log;

public class Utils {
    public static void printString(InputStream in) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line = null;
        do {
            try {
                line = reader.readLine();
                if (line != null) log(line);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } while (line != null);
    }
}
