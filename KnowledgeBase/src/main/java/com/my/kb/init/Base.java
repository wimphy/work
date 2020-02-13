package com.my.kb.init;

public class Base {
    public static int STATIC_BASE = 1;

    static {
        STATIC_BASE++;
        System.out.println("base static " + STATIC_BASE);

        //deadlock warning here
        System.out.println(Sub.STATIC_SUB);
    }

    public int varBase = 10;

    public Base() {
        varBase++;
        System.out.println("base constructor " + varBase);
    }


}
