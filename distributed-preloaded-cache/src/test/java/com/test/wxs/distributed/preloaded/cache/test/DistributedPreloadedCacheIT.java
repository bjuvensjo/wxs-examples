package com.test.wxs.distributed.preloaded.cache.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.test.wxs.cache.Cache;
import com.test.wxs.model.account.Account;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class DistributedPreloadedCacheIT {
    @Autowired
    private Cache<DistributedPreloadedCacheTestKey, Account> cache;

    @Test
    public void testSingleAccount() {
        DistributedPreloadedCacheTestKey key = new DistributedPreloadedCacheTestKey("21");
        Account expected = new Account("21", 21);

        Account actual = cache.get(key);
        assertEquals(expected, actual);
    }

    @Test
    public void testGetListOfAccounts() {
        List<DistributedPreloadedCacheTestKey> keys = new ArrayList<DistributedPreloadedCacheTestKey>();
        DistributedPreloadedCacheTestKey key1 = new DistributedPreloadedCacheTestKey("30");
        DistributedPreloadedCacheTestKey key2 = new DistributedPreloadedCacheTestKey("31");
        keys.add(key1);
        keys.add(key2);
        
        List<Account> result = cache.get(keys);
        assertEquals(2, result.size());
    }
}
