package com.my.kb.nio;

import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;

import static com.my.kb.utils.EasyLogger.log;

public class ByteBufferTest {
    @Test
    public void test() throws Exception {
        String s = "abc";//3 bytes data
        Assert.assertEquals(3, s.getBytes().length);

        ByteBuffer buffer = ByteBuffer.allocate(5);//initial pointers
        Assert.assertEquals(5, buffer.limit());
        Assert.assertEquals(5, buffer.capacity());
        Assert.assertEquals(0, buffer.position());

        buffer.put(s.getBytes());//write 3 bytes data
        Assert.assertEquals(5, buffer.limit());
        Assert.assertEquals(5, buffer.capacity());
        Assert.assertEquals(3, buffer.position());

        buffer.flip();//set position to 0
        Assert.assertEquals(3, buffer.limit());
        Assert.assertEquals(5, buffer.capacity());
        Assert.assertEquals(0, buffer.position());

        byte b = buffer.get();//get 1 byte data, and set current pos
        Assert.assertEquals('a', b);
        Assert.assertEquals(3, buffer.limit());
        Assert.assertEquals(5, buffer.capacity());
        Assert.assertEquals(1, buffer.position());

        buffer.rewind();//set current pos to 0
        Assert.assertEquals(3, buffer.limit());
        Assert.assertEquals(5, buffer.capacity());
        Assert.assertEquals(0, buffer.position());

        buffer.get();
        buffer.get();
        buffer.mark();//mark current pos
        Assert.assertEquals(3, buffer.limit());
        Assert.assertEquals(5, buffer.capacity());
        Assert.assertEquals(2, buffer.position());

        buffer.get();//get 1 more byte
        Assert.assertEquals(3, buffer.limit());
        Assert.assertEquals(5, buffer.capacity());
        Assert.assertEquals(3, buffer.position());

        buffer.reset();//reset current pos to marked pos
        Assert.assertEquals(3, buffer.limit());
        Assert.assertEquals(5, buffer.capacity());
        Assert.assertEquals(2, buffer.position());

        buffer.clear();//clear all data, which equals the initial state
        Assert.assertEquals(5, buffer.limit());
        Assert.assertEquals(5, buffer.capacity());
        Assert.assertEquals(0, buffer.position());
    }

}
