package com.my.kb.seal;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class ChildClass1 extends ParentClass {
    public static void main(String[] args) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:sss");
        Date date = Calendar.getInstance().getTime();
        System.out.println(df.format(date));
    }
}
