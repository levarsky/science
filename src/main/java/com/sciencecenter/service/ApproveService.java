package com.sciencecenter.service;

import com.sciencecenter.model.User;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApproveService implements TaskListener {

    @Autowired
    private UserService userService;

    @Override
    public void notify(DelegateTask delegateTask) {

        IdentityService identityService = delegateTask.getProcessEngine().getIdentityService();

        if ((boolean)delegateTask.getVariable("approve")){

            String username=delegateTask.getVariable("username").toString();
            User user = userService.getUser(username);
            user.setReviewer(true);

            identityService.createMembership(username,"reviewer");
            userService.saveToDBWithRole(user,"reviewer");
        }

    }
}
