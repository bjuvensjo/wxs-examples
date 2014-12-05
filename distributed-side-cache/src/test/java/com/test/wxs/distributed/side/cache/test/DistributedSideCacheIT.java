package com.test.wxs.distributed.side.cache.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.test.wxs.cache.Cache;
import com.test.wxs.model.account.Account;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class DistributedSideCacheIT {
    @Autowired
    private Cache<DistributedSideCacheTestKey, Account> cache;
    private int numberOfEntries = 20;

    @Before
    public void init() {
        for (int i = 0; i < numberOfEntries; i++) {
            DistributedSideCacheTestKey key = new DistributedSideCacheTestKey(String.valueOf(i));
            Account value = new Account(String.valueOf(i), i);
            cache.put(key, value);
        }
    }

    @After
    public void clean() {
        for (int i = 0; i < numberOfEntries; i++) {
            DistributedSideCacheTestKey key = new DistributedSideCacheTestKey(String.valueOf(i));
            cache.remove(key);
        }
    }

    @Test
    public void testSingleAccount() {
        DistributedSideCacheTestKey key = new DistributedSideCacheTestKey("1");
        Account expected = new Account("1", 1);

        Account actual = cache.get(key);

        assertEquals(expected, actual);
        assertNotSame(expected, actual);
    }

    @Test
    public void testGetListOfAccounts() {
        List<DistributedSideCacheTestKey> keys = new ArrayList<DistributedSideCacheTestKey>();

        keys.add(new DistributedSideCacheTestKey("10"));
        keys.add(new DistributedSideCacheTestKey("11"));

        List<Account> result = cache.get(keys);

        assertEquals(2, result.size());
    }
}
