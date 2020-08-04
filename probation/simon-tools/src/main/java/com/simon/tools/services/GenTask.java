package com.simon.tools.services;

import com.simon.tools.utils.Strings;
import org.apache.commons.csv.CSVRecord;

public class GenTask {
    private String fileName;
    private String tableName;
    private String description;
    private String id;
    private long rowNumber;

    public GenTask(CSVRecord record, String fileName) {
        this.fileName = fileName;
        tableName = record.get(1);
        description = record.get(3);
        id = record.get(0);
        rowNumber = record.getRecordNumber();
        description = Strings.toSQLString(description);
    }

    public String getFileName() {
        return fileName;
    }

    public String getTableName() {
        return tableName;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public long getRowNumber() {
        return rowNumber;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return rowNumber + ":" + getId();
    }
}
