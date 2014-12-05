package com.test.wxs.cache.wxs;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.test.wxs.cache.Cache;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class LocalInlineCacheTest {

    @Autowired
    private Cache<LocalInlineCacheTestKey, String> cache;

    @Test
    public void testGet1() {
        LocalInlineCacheTestKey key = new LocalInlineCacheTestKey("foo", "bar");
        
        String expected = key.toString();
        
        String actual = cache.get(key);
        assertEquals(expected, actual);
        
        actual = cache.get(key);
        assertEquals(expected, actual);
    }
}
