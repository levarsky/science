package com.sciencecenter.service;

import com.sciencecenter.model.VerificationToken;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


public class EmailDelService implements JavaDelegate {

    @Autowired
    private EmailService emailService;

    @Autowired
    private VerificationTokenService verificationTokenService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        String processInstanceId = delegateExecution.getProcessInstance().getProcessInstanceId();

        VerificationToken verificationToken = verificationTokenService.createVerificationToken(delegateExecution.getVariable("username").toString());

        String recipientAddress = delegateExecution.getVariable("email").toString();

        String confirmationString  = "To confirm your account, please click here : "
                + "http://localhost:8090/science/auth/confirm?token=" + verificationToken.getVerificationToken() +"&process="+processInstanceId;

        emailService.sendEmail(recipientAddress,"Confirm your account",confirmationString);

    }
}
