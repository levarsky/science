package com.sciencecenter.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
public class EmailSetEditor implements JavaDelegate {


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

    }
}
