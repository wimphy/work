package com.my.kb.io;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Files {
    public static InputStream getInputStream(String path) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        return loader.getResourceAsStream(path);
    }

    public static void jsonToCsv(String path) throws IOException {
        String csv = path;
        if (path.endsWith(".json")) {
            csv = csv.substring(0, csv.length() - 5) + ".csv";
        } else {
            csv += ".csv";
        }
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(new FileReader(path));
        node = node.at("/results/0/result");

        //csv
        List<String> header = new ArrayList<>();
        List<List<String>> csvData = new ArrayList<>();

        for (int i = 0; i < node.size(); i++) {
            JsonNode e = node.get(i);
            List<String> row = new ArrayList<>();
            Iterator<String> iterator = e.fieldNames();
            while (iterator.hasNext()) {
                String key = iterator.next();
                if (!header.contains(key)) {
                    header.add(key);
                }
                String value = e.get(key).asText();
                row.add(value);
            }
            csvData.add(row);
        }
        csvData.add(0, header);

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csv)))) {
            for (List<String> row : csvData) {
                String line = String.join("||", row.toArray(new String[0]));
                writer.write(line);
                writer.newLine();
            }
        }

    }
}
