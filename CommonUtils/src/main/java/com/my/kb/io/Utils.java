package com.my.kb.io;

import java.io.*;

import static com.my.kb.utils.SiLogger.log;

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

    public static char[][] readMatrix(String name) {
        try {
            SiJson json = SiJson.readFromResource(name);
            if (!json.isArray()) {
                return null;
            }
            int len = json.length();
            if (len == 0) {
                return null;
            }
            int hei = json.getChild(0).length();
            if (hei == 0) {
                return null;
            }
            char[][] res = new char[len][hei];
            for (int x = 0; x < len; x++) {
                SiJson row = json.getChild(x);
                if (!row.isArray() || row.length() != hei) {
                    return null;
                }
                for (int y = 0; y < hei; y++) {
                    String v = row.getString(y + "");
                    if (v.length() != 1) {
                        break;
                    }
                    res[x][y] = v.charAt(0);
                }
            }
            return res;
        } catch (IOException ignore) {
            return null;
        }
    }
}
