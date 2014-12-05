package com.test.wxs.service;

import java.io.Serializable;

public class ResultStatus implements Serializable {
    private static final long serialVersionUID = 8560063794512898059L;
    private int numberOfRetreivedValues;
    private int numberOfRetrievedFailures;

    public ResultStatus(int numberOfRetreivedValues, int numberOfRetrievedFailures) {
        super();
        this.numberOfRetreivedValues = numberOfRetreivedValues;
        this.numberOfRetrievedFailures = numberOfRetrievedFailures;
    }

    public int getNumberOfRetreivedValues() {
        return numberOfRetreivedValues;
    }

    public int getNumberOfRetrievedFailures() {
        return numberOfRetrievedFailures;
    }
}
