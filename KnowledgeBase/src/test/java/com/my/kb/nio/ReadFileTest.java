package com.my.kb.nio;

import org.junit.Test;

public class ReadFileTest {
    @Test
    public void testBioRead() {
        ReadFile readFile = new ReadFile();
        readFile.bioRead();
        System.out.println("\r\n".getBytes().length);
    }

    @Test
    public void testNioRead() {
        ReadFile readFile = new ReadFile();
        readFile.nioRead();
    }
}
