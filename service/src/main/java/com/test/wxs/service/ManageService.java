package com.test.wxs.service;

import java.util.List;

public interface ManageService {

    void startTimer();

    void stopTimer();

    CommitStatus loadAll();

    @SuppressWarnings("rawtypes")
    CommitStatus load(List keys);
}
