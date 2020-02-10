package com.sciencecenter.service;

import com.sciencecenter.model.Magazine;
import com.sciencecenter.model.User;
import com.sciencecenter.model.UserMagazine;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailMainEditorAuthor implements JavaDelegate {

    @Autowired
    private EmailService emailService;

    @Autowired
    private MagazineService magazineService;

    @Autowired
    private UserService userService;


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        System.out.println(delegateExecution.getVariables().toString());

        Magazine magazine = magazineService.findByISSN(delegateExecution.getVariable("magazine").toString());

        UserMagazine  userMagazine = magazineService.getUserMagazine(magazine.getId(),"main_editor");

        User userMainEditor = userMagazine.getUser();

        User userAuthor = userService.getUserAuth();


        String messageAuthor  = "Author "+ userAuthor.getUsername() + " wants to create new Issue(content) , new overview task is present!";
        String messageUser  = "You "+ userAuthor.getUsername() + " submitted new Issue(content),wait for approval !";

        emailService.sendEmail(userAuthor.getEmail(),"Submission",messageUser);
        emailService.sendEmail(userMainEditor.getEmail(),"Overview",messageAuthor);


        delegateExecution.setVariable("main_editor",userMainEditor.getUsername());


    }
}
