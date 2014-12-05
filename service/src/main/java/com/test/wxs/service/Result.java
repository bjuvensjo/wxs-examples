package com.test.wxs.service;

import java.io.Serializable;
import java.util.Map;

public class Result implements Serializable {
    private static final long serialVersionUID = 603062133027074223L;
    @SuppressWarnings("rawtypes")
    private Map keysAndValues;
    private ResultStatus resultStatus;

    @SuppressWarnings("rawtypes")
    public Result(Map keysAndValues, ResultStatus resultStatus) {
        super();
        this.keysAndValues = keysAndValues;
        this.resultStatus = resultStatus;
    }

    @SuppressWarnings("rawtypes")
    public Map getKeysAndValues() {
        return keysAndValues;
    }

    public ResultStatus getResultStatus() {
        return resultStatus;
    }
}
