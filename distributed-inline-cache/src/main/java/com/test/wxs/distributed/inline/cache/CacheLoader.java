package com.test.wxs.distributed.inline.cache;

import java.util.ArrayList;
import java.util.List;

import com.test.wxs.model.CacheObject;
import com.test.wxs.model.account.Account;
import com.ibm.websphere.objectgrid.BackingMap;
import com.ibm.websphere.objectgrid.Session;
import com.ibm.websphere.objectgrid.TxID;
import com.ibm.websphere.objectgrid.plugins.Loader;
import com.ibm.websphere.objectgrid.plugins.LoaderException;
import com.ibm.websphere.objectgrid.plugins.LogSequence;
import com.ibm.websphere.objectgrid.plugins.OptimisticCollisionException;

public class CacheLoader implements Loader {

	@Override
	public void batchUpdate(TxID txID, LogSequence logSequence) throws LoaderException, OptimisticCollisionException {
	}

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public List get(TxID txID, List keys, boolean forUpdate) throws LoaderException {
        List result = new ArrayList();
        for (int i = 0; i < keys.size(); i++) {
            String serviceRequestId = String.valueOf(i);
            String key = (String) keys.get(i);
            Account account = getAccount(key);
            CacheObject cacheObject = new CacheObject(serviceRequestId, account);
            result.add(cacheObject);
        }
        return result;
    }

    // TODO Put this in a service in a separate module
    private Account getAccount(String key) {
        Account account = new Account(key, Integer.parseInt(key));
        return account;
    }

	@Override
	public void preloadMap(Session session, BackingMap backingMap) throws LoaderException {
	}
}
