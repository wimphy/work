package com.simon.tools.services;

import com.simon.tools.oracle.rollout.DeployGenerator;
import com.simon.tools.oracle.rollout.RollBackGenerator;
import com.simon.tools.oracle.rollout.UpdateGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.concurrent.ExecutorService;

@Service
public class BatchUpdate {
    private static final Logger logger = LoggerFactory.getLogger(BatchUpdate.class);

    private @Autowired UpdateGenerator updateGenerator;
    private @Autowired DeployGenerator deployGenerator;
    private @Autowired RollBackGenerator rollBackGenerator;
    protected @Autowired ExecutorService executorService;

    public void doTaskByFile(String fileName) {
        new Thread(() -> {
            try (Reader reader = new FileReader(fileName);
                 CSVParser parser = new CSVParser(reader, CSVFormat.EXCEL)) {

                for (CSVRecord record : parser) {
                    String v1 = record.get(0);
                    if (v1.equals("OBJECTID")) {
                        logger.info("bypass header");
                        continue;
                    }
                    GenTask task = new GenTask(record, fileName);
                    //updateGenerator.take(task);
                    //deployGenerator.take(task);
                    rollBackGenerator.take(task);
                }
                updateGenerator.stop();
                deployGenerator.stop();
                rollBackGenerator.stop();
                executorService.shutdown();
            } catch (IOException | InterruptedException e) {
                logger.error("failed to process csv file ", e);
            }
        }).start();
    }

    @PostConstruct
    public void processDescriptions() {
        doTaskByFile("C:\\simon\\refinitiv\\cdb\\tasks\\1684\\Rebranding_descriptions.csv");
        doTaskByFile("C:\\simon\\refinitiv\\cdb\\tasks\\1684\\rebranding urls.csv");
    }

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("batchUpdateContext.xml");
        context.registerShutdownHook();
        context.start();
    }
}
