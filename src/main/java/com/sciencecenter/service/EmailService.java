package com.sciencecenter.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String recipientAddress,String subject,String text){

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientAddress);
        message.setSubject(subject);
        message.setText(text);

        javaMailSender.send(message);
    }


}
