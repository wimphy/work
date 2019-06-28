package com.my.kb;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Vector;

public class MemLeakTest {
    private static Vector<Object> vector = new Vector<>();

    @Test
    public void staticCollection() {
        for (int i = 0; i < 100; i++) {
            Object o = new Object();
            vector.add(o);
            o = null;
        }
        //o=null will not release the object ...
    }

    @Test
    public void hashSetTest() {
        var set = new HashSet<Person>();
        Person p1 = new Person("Peter");
        Person p2 = new Person("Tome");
        Person p3 = new Person("Lin");
        set.add(p1);
        set.add(p2);
        set.add(p3);

        Assert.assertEquals(set.size(), 3);
        p1.setAge(2);
        set.remove(p1);
        Assert.assertEquals(set.size(), 3);//failed to remove due to hashcode change

        set.add(p1);
        Assert.assertEquals(set.size(), 4);

        for (Person p : set) {
            System.out.println(p);
        }
    }
}
