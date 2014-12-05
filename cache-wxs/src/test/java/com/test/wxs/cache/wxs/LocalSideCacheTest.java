package com.test.wxs.cache.wxs;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.test.wxs.cache.Cache;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class LocalSideCacheTest {

    @Autowired
    private Cache<LocalSideCacheTestKey, String> cache;

    @Before
    public void init() {
        LocalSideCacheTestKey key1 = new LocalSideCacheTestKey("1");
        LocalSideCacheTestKey key2 = new LocalSideCacheTestKey("2");
        cache.put(key1, "value1");
        cache.put(key2, "value2");
    }

    @Test
    public void testGet1() {
        LocalSideCacheTestKey key1 = new LocalSideCacheTestKey("1");
        assertEquals("value1", cache.get(key1));
    }

    @Test
    public void testGetListofObjects() {
        List<LocalSideCacheTestKey> keys = new ArrayList<LocalSideCacheTestKey>();
        LocalSideCacheTestKey key1 = new LocalSideCacheTestKey("1");
        LocalSideCacheTestKey key2 = new LocalSideCacheTestKey("2");
        keys.add(key1);
        keys.add(key2);
        List<String> result = cache.get(keys);
        assertEquals("value1", result.get(0));
        assertEquals("value2", result.get(1));
    }
}
