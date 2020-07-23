package com.simon.tools;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("batchUpdateContext.xml");
        context.registerShutdownHook();
        context.start();
    }
}
