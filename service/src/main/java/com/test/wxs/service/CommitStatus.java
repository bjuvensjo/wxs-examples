package com.test.wxs.service;

import java.io.Serializable;

public class CommitStatus implements Serializable {
    private static final long serialVersionUID = 1L;
    private int numberOfCommittedValues;
    private int numberOfCommitFailures;

    public CommitStatus() {
        this(0, 0);
    }

    public CommitStatus(int numberOfCommitedValues, int numberOfCommitFailures) {
        super();
        this.numberOfCommittedValues = numberOfCommitedValues;
        this.numberOfCommitFailures = numberOfCommitFailures;
    }

    public int getNumberOfCommitedValues() {
        return numberOfCommittedValues;
    }

    public int getNumberOfCommitFailures() {
        return numberOfCommitFailures;
    }

    @Override
    public String toString() {
        return "CommitStatus [numberOfCommittedValues=" + numberOfCommittedValues + ", numberOfCommitFailures=" + numberOfCommitFailures
                + "]";
    }
}
