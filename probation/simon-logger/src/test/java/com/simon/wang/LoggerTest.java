package com.simon.wang;

import org.junit.jupiter.api.Test;

public class LoggerTest {

    @Test
    public void testLog(){
        Logger.INSTANCE.log("test no args");
    }
}
