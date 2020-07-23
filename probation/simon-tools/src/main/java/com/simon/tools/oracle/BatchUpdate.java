package com.simon.tools.oracle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.sql.SQLException;

@Service
public class BatchUpdate {
    private static final Logger logger = LoggerFactory.getLogger(BatchUpdate.class);
    private static final String sqlFormat = "update %s set description=%s where id=%s";
    private @Autowired BatchDao batchDao;
    private @Value("${db.rows.batch}") int batchSize;

    private String generateSQL(CSVRecord record) {
        String tableName = record.get(1);
        String description = record.get(3);
        //!!, [], {}, (), <>
        //skip special chars
        if (!description.contains("!")) {
            description = String.format("q'!%s!'", description);
        } else if (!description.contains("[") && !description.contains("]")) {
            description = String.format("q'[%s]'", description);
        } else if (!description.contains("{") && !description.contains("}")) {
            description = String.format("q'{%s}'", description);
        } else if (!description.contains("(") && !description.contains(")")) {
            description = String.format("q'(%s)'", description);
        } else if (!description.contains("<") && !description.contains(">")) {
            description = String.format("q'<%s>'", description);
        } else {
            logger.error("too many special chars!!!");
            return null;
        }
        String id = record.get(0);
        return String.format(sqlFormat, tableName, description, id);
    }

    public void doTaskByFile(String fileName) {
        new Thread(() -> {
            int rowNumber = 0;
            String sql = null;

            String slqFileName = fileName.substring(0, fileName.lastIndexOf(".")) + ".sql";
            try (Reader reader = new FileReader(fileName);
                 CSVParser parser = new CSVParser(reader, CSVFormat.EXCEL);
                 FileWriter writer = new FileWriter(slqFileName);
                 BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
                bufferedWriter.append("set define off;");
                bufferedWriter.newLine();
                for (CSVRecord record : parser) {
                    String v1 = record.get(0);
                    if (v1.equals("OBJECTID")) {
                        logger.info("bypass header");
                        continue;
                    }

                    sql = generateSQL(record);
                    bufferedWriter.append(sql).append(";");
                    bufferedWriter.newLine();
                    if (rowNumber > 0 && rowNumber % batchSize == 0) {
                        bufferedWriter.append("commit;");
                        bufferedWriter.newLine();
                    }
                    batchDao.addTask(sql);
                    rowNumber++;
                }
                bufferedWriter.append("commit;");
                bufferedWriter.newLine();
                batchDao.stop();
            } catch (IOException | InterruptedException e) {
                logger.error(rowNumber + ":" + sql);
                logger.error("failed to process csv file ", e);
            }
        }).start();
    }

    @PostConstruct
    public void processDescriptions() {
        doTaskByFile("C:\\1.csv");
        doTaskByFile("C:\\2.csv");//new thread to add to queue
    }

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("batchUpdateContext.xml");
        context.registerShutdownHook();
        context.start();
    }
}
