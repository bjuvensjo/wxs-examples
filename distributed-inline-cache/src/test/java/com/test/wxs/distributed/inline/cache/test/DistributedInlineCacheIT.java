package com.test.wxs.distributed.inline.cache.test;

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
public class DistributedInlineCacheIT {    
    @Autowired
    private Cache<DistributedInlineCacheTestKey, Account> cache;
        
    @Test
    public void testSingleAccount() {
        DistributedInlineCacheTestKey key = new DistributedInlineCacheTestKey("1");
        Account expected = new Account("1", 1);
        
        Account actual = cache.get(key);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testGetListOfAccounts() {
        List<DistributedInlineCacheTestKey> keys = new ArrayList<DistributedInlineCacheTestKey>();
        DistributedInlineCacheTestKey key1 = new DistributedInlineCacheTestKey("11");
        DistributedInlineCacheTestKey key2 = new DistributedInlineCacheTestKey("10");
        keys.add(key1);
        keys.add(key2);
        List<Account> result = cache.get(keys);
        assertEquals(2, result.size());
    }
}
