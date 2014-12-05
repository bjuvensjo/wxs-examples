package com.test.wxs.agent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.naming.InitialContext;

import com.test.wxs.model.CacheObject;
import com.test.wxs.model.account.Account;
import com.test.wxs.service.CommitStatus;
import com.test.wxs.service.LoadService;
import com.test.wxs.service.Result;
import com.test.wxs.service.ResultStatus;
import com.ibm.websphere.objectgrid.ObjectGridException;
import com.ibm.websphere.objectgrid.ObjectMap;
import com.ibm.websphere.objectgrid.Session;
import com.ibm.websphere.objectgrid.datagrid.ReduceGridAgent;

public class LoaderAgent implements ReduceGridAgent {
	private static final long serialVersionUID = 4576616555376993858L;
	private String loadServiceJndiName;
	private int loadSize = -1;

	@Override
	public Object reduce(Session session, ObjectMap map) {
		throw new UnsupportedOperationException();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Object reduce(Session session, ObjectMap map, Collection keys) {
		CommitStatus commitStatus = null;
		ResultStatus resultStatus = null;
		try {
			List theKeys = new ArrayList(keys);
			// Lock the keys until commit. If the keys do not exists in wxs, there will be cache misses (see
			// LoaderPlugin)
			map.getAllForUpdate(theKeys);
			if (loadSize <= 0) {
				resultStatus = load(map, theKeys);
			} else {
				resultStatus = loadByBatch(map, theKeys);
			}
			// TODO Commit here or only in LoadStrategy? (Don't forget to consider the finally clause!
			session.commit();
		} catch (Exception e) {
//			log.error("update error.", e);
		    e.printStackTrace(System.err);
		} finally {
			if (session.isTransactionActive()) {
				try {
					session.rollback();
					commitStatus = new CommitStatus(0, keys.size());
				} catch (Exception e) {
//					log.error("rollback error", e);
					e.printStackTrace(System.err);
				}
			} else {
				commitStatus = new CommitStatus(resultStatus.getNumberOfRetreivedValues(),
				        resultStatus.getNumberOfRetrievedFailures());
			}
		}
		return commitStatus;
	}

	@Override
	public Object reduceResults(@SuppressWarnings("rawtypes") Collection statuses) {
		int numberOfCommitedValues = 0;
		int numberOfCommitFailures = 0;
		for (Object status : statuses) {
			CommitStatus commitStatus = (CommitStatus) status;
			numberOfCommitedValues += commitStatus.getNumberOfCommitedValues();
			numberOfCommitFailures += commitStatus.getNumberOfCommitFailures();
		}
		return new CommitStatus(numberOfCommitedValues, numberOfCommitFailures);
	}

	@SuppressWarnings("rawtypes")
	private ResultStatus load(ObjectMap map, List keys) throws ObjectGridException {
		Result loadResult = getLoadService().getValues(keys);
		map.putAll(loadResult.getKeysAndValues());
		return loadResult.getResultStatus();
	}

	@SuppressWarnings("rawtypes")
	private ResultStatus loadByBatch(ObjectMap map, List keys) throws ObjectGridException {
		List<ResultStatus> resultStatuses = new ArrayList<ResultStatus>();
		int start = 0;
		int end = start + loadSize;
		while (start < keys.size()) {
			List keysToLoad = keys.subList(start, Math.min(end, keys.size()));
			ResultStatus resultStatus = load(map, keysToLoad);
			resultStatuses.add(resultStatus);
			start = end;
			end = start + loadSize;
		}
		// Merge ResultStatuses
		int numberOfRetreivedValues = 0;
		int numberOfRetrievedFailures = 0;
		for (ResultStatus resultStatus : resultStatuses) {
			numberOfRetreivedValues += resultStatus.getNumberOfRetreivedValues();
			numberOfRetrievedFailures += resultStatus.getNumberOfRetrievedFailures();
		}
		return new ResultStatus(numberOfRetreivedValues, numberOfRetrievedFailures);
	}

	// TODO Implement correctly
	private LoadService getLoadService() {
	    return new LoadService() {
            
            @SuppressWarnings("rawtypes")
            @Override
            public Result getValues(List keys) {               
                Map<String, Serializable> keysAndValues = new HashMap<String, Serializable>();
                for (Object key : keys) {
                    String theKey = (String)key;
                    // TODO Set id correctly!
                    String id = UUID.randomUUID().toString();
                    Account value = new Account(theKey, Integer.parseInt(theKey));
                    CacheObject cacheObject = new CacheObject(id, value);
                    keysAndValues.put(theKey, cacheObject);
                }
                ResultStatus resultStatus = new ResultStatus(keys.size(), 0);
                System.err.println("########################" + keysAndValues);
                return new Result(keysAndValues, resultStatus);
            }
        };
//		LoadService loadService = null;
//		try {
//			InitialContext ctx = new InitialContext();
//			loadService = (LoadService) ctx.lookup(loadServiceJndiName);
//
//		} catch (Exception e) {
//			log.error("Can not lookup load service with jndi name: {}", loadServiceJndiName, e);
//		}
//		return loadService;
	}

	public void setLoadServiceJndiName(String loadServiceJndiName) {
		this.loadServiceJndiName = loadServiceJndiName;
	}

	public void setLoadSize(int loadSize) {
		this.loadSize = loadSize;
	}
}
