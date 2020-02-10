package com.sciencecenter.service;

import com.sciencecenter.model.Magazine;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OpenAccessCheck implements JavaDelegate {

    @Autowired
    private MagazineService magazineService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        System.out.println(delegateExecution.getVariables().toString());

        delegateExecution.setVariable("isFormat",true);

        Magazine magazine = magazineService.findByISSN(delegateExecution.getVariable("magazine").toString());


        System.out.println(magazine.getPayment());

        if (magazine.getPayment().equals("OA")){
            delegateExecution.setVariable("open_access_check",true);
        }else
            delegateExecution.setVariable("open_access_check",false);

    }
}
