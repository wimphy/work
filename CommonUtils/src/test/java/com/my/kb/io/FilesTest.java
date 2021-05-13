package com.my.kb.io;

import org.junit.Test;

import java.io.IOException;

public class FilesTest {
    @Test
    public void testJsonToCsv() throws IOException {
        Files.jsonToCsv("C:\\simon\\refinitiv\\cdb\\tasks\\mvp\\papi\\EEX_facilities.json");
    }
}
