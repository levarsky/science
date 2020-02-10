package com.sciencecenter.service;

import com.sciencecenter.model.Magazine;
import com.sciencecenter.model.UserMagazine;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MembershipCheck implements JavaDelegate {

    @Autowired
    private MagazineService magazineService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        System.out.println(delegateExecution.getVariables().toString());

        Magazine magazine = magazineService.findByISSN(delegateExecution.getVariable("magazine").toString());

        boolean member = magazineService.existsBy(magazine.getId());

        System.out.println(member);

        if (member){
            delegateExecution.setVariable("membership_check",true);
        }else
            delegateExecution.setVariable("membership_check",false);


    }
}
