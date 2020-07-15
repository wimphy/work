package com.simon.wang;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public enum Logger {
    INSTANCE;

    private String logFile = null;
    private SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public void log(String msg, String... args) {
        String line = "[%s] - " + msg;
        Calendar calendar = Calendar.getInstance();
        ArrayList<String> arr = new ArrayList<>();
        arr.add(SDF.format(calendar.getTime()));
        Collections.addAll(arr, args);
        line = String.format(line, arr.toArray());
        write(line);
    }

    public String getLogFile() {
        return logFile;
    }

    public void setLogFile(String logFile) {
        this.logFile = logFile;
    }

    private void write(String s) {
        System.out.println(s);
        writeToFile(s);
    }

    private void writeToFile(String s) {
        String filePath = getLogFile();
        filePath = filePath == null ? "target/log.txt" : filePath;
        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.append(s).append("\r\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
