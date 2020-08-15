import org.testng.annotations.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CommonTest {
    @Test
    public void testStringFormat() {
        String s = String.format("%1$s, world, %1$s", "hello");
        System.out.println(s);
        s = String.format("http://localhost:5331/test?id=%s&d=${d}T00%%3A00%%3A00", 1);
        System.out.println(s);
    }

    @Test
    public void testLineLength() throws Exception {
        String p = System.getProperty("S_SQL");
        String d = System.getProperty("D_SQL");
        try (BufferedReader reader = new BufferedReader(new FileReader(p));
             BufferedWriter writer = new BufferedWriter(new FileWriter(d))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.length() > 2800) {
                    writer.append(line);
                    writer.newLine();
                    String id = line.substring(line.lastIndexOf('=') + 1, line.length() - 1);
                    System.out.println(id);
                }
            }
        }
    }
}
