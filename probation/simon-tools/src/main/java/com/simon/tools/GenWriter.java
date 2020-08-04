package com.simon.tools;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class GenWriter extends BufferedWriter {
    private String outFileName;

    public GenWriter(String outFileName) throws IOException {
        super(new FileWriter(outFileName));
        this.outFileName = outFileName;
    }

    public String getOutFileName() {
        return outFileName;
    }

    @Override
    public String toString() {
        return outFileName;
    }
}
