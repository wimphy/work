package com.my.kb;

import org.junit.Test;

import org.junit.Assert;

public class JDK11NewFeatures {
    @Test
    public void TestEmpties() {
        //Note that \u2000 is above \u0020 and not considered whitespace by trim()

        //ASCII
        String s = "\t abc \n";
        Assert.assertEquals("abc", s.trim());
        Assert.assertEquals("abc", s.strip());

        //Unicode
        char c = '\u2000';
        s = c + "abc" + c;
        Assert.assertTrue(Character.isWhitespace(c));
        Assert.assertEquals(s, s.trim());
        Assert.assertEquals("abc", s.strip());
    }
}
