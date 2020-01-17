package com.sciencecenter.service;

import com.sciencecenter.model.Field;
import com.sciencecenter.repository.RoleRepository;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public class ValidationService implements JavaDelegate {

    @Autowired
    private DiagramService diagramService;

    @Autowired
    private UserService userService;

    @Autowired
    private FieldService fieldService;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println(delegateExecution.getVariables().toString());

        com.sciencecenter.model.User userReq = (com.sciencecenter.model.User)  delegateExecution.getVariable("signUp");



        System.out.println(userReq.getUsername());

        delegateExecution.setVariable("userVal",true);

        IdentityService identityService = delegateExecution.getProcessEngine().getIdentityService();

        User user = identityService.newUser(userReq.getUsername());
        user.setEmail(userReq.getEmail());
        user.setFirstName(userReq.getFirstName());
        user.setLastName(userReq.getLastName());
        user.setPassword(userReq.getPassword());

        userReq.setPassword("");
        delegateExecution.setVariable("signUp",userReq);

        userReq.setReviewer(false);

        userService.checkUser(userReq);

        List<Field> fields= fieldService.compareAndAdd(userReq.getFields());

        userReq.setFields(fields);
        userService.saveToDBWithRole(userReq,"user");


        identityService.saveUser(user);
        identityService.createMembership(userReq.getUsername(),"user");
    }
}
