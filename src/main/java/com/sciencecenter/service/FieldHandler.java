package com.sciencecenter.service;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;

public class FieldHandler implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {

        System.out.println(delegateTask.getVariables().toString());







    }
}
