package com.my.kb;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BasicTypeTest {
    @Test(expected = ClassCastException.class)
    public void testCast() throws Exception{
        List<Object> list = new ArrayList<>();
        Long l = 1L;
        list.add(l);

        String s = (String) list.get(0);
    }
}
