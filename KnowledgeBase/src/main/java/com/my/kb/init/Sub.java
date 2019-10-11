package com.my.kb.init;

public class Sub extends Base {
    public static int STATIC_SUB = 100;

    static {
        STATIC_SUB++;
        System.out.println("sub static " + Base.STATIC_BASE);
        System.out.println("sub static " + STATIC_SUB);
    }

    public int varSub = 1000;

    public Sub() {
        varSub++;
        System.out.println("sub constructor " + varSub);
    }

    public void test() {
        System.out.println("test{} invoked");
    }

    public static void main(String[] args) {
        Sub sub = new Sub();
        sub.test();
    }
}
