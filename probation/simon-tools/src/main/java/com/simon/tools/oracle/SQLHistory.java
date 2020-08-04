package com.simon.tools.oracle;

import com.simon.tools.utils.Common;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class SQLHistory {
    private static final Logger logger = LoggerFactory.getLogger(SQLHistory.class);

    private ReentrantLock lock = new ReentrantLock();
    private ArrayList<String> savedList = new ArrayList<>();
    private FileWriter writer;
    private BufferedWriter bufferedWriter;

    public SQLHistory(String fileName) {
        try {
            Path filePath = Paths.get("logs", fileName);
            if (Files.exists(filePath)) {
                try (FileReader reader = new FileReader(filePath.toFile());
                     BufferedReader bufferedReader = new BufferedReader(reader)) {
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        savedList.add(line);
                    }
                }
                Files.delete(filePath);
            }
            writer = new FileWriter(filePath.toFile());
            bufferedWriter = new BufferedWriter(writer);
        } catch (IOException e) {
            logger.warn("failed to initialize history, all tasks will be added", e);
        }
    }

    public void save(String value) {
        lock.lock();
        try {
            bufferedWriter.append(value);
            bufferedWriter.newLine();
        } catch (IOException e) {
            logger.warn(value + " :failed to save history", e);
        }
        lock.unlock();
    }

    public boolean isSaved(String value) {
        boolean res = false;
        lock.lock();
        if (savedList.contains(value)) {
            savedList.remove(value);
            res = true;
        }
        lock.unlock();
        return res;
    }

    @PreDestroy
    public void close() {
        Common.close(bufferedWriter);
        Common.close(writer);
    }
}
