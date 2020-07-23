package com.simon.tools.oracle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

@Service
public class BatchUpdate {
    private static final Logger logger = LoggerFactory.getLogger(BatchUpdate.class);
    private static final String sqlFormat = "update %s set description=%s where id=%s";
    private @Autowired JdbcTemplate jdbcTemplate;

    private String generateSQL(CSVRecord record) {
        String tableName = record.get(1);
        String description = record.get(3);
        //!!, [], {}, (), <>
        //skip special chars
        if (!description.contains("!")) {
            description = String.format("q\"!%s!\"", description);
        } else if (!description.contains("[") && !description.contains("]")) {
            description = String.format("q\"[%s]\"", description);
        } else if (!description.contains("{") && !description.contains("}")) {
            description = String.format("q\"{%s}\"", description);
        } else if (!description.contains("(") && !description.contains(")")) {
            description = String.format("q\"(%s)\"", description);
        } else if (!description.contains("<") && !description.contains(">")) {
            description = String.format("q\"<%s>\"", description);
        } else {
            logger.error("too many special chars!!!");
            return null;
        }
        String id = record.get(0);
        return String.format(sqlFormat, tableName, description, id);
    }

    private void writeSQLToFile(String sql) {
    }

    public void doTaskByFile(String fileName) {
        try (Reader reader = new FileReader(fileName);
             CSVParser parser = new CSVParser(reader, CSVFormat.EXCEL)) {
            for (CSVRecord record : parser) {
                String v1 = record.get(0);
                if (v1.equals("OBJECTID")) {
                    logger.info("bypass header");
                    continue;
                }

                String sql = generateSQL(record);
                writeSQLToFile(sql);
                jdbcTemplate.batchUpdate(sql);

            }
        } catch (IOException e) {
            logger.error("failed to read csv file ", e);
        }
    }

    @PostConstruct
    public void processDescriptions() {
        doTaskByFile("C:\\simon\\refinitiv\\cdb\\tasks\\1684\\Rebranding_descriptions.csv");
    }

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("batchUpdateContext.xml");
        context.registerShutdownHook();
        context.start();
    }
}
