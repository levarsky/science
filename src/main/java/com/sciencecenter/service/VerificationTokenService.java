package com.sciencecenter.service;

import com.sciencecenter.model.User;
import com.sciencecenter.model.VerificationToken;
import com.sciencecenter.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Calendar;
import java.util.UUID;

@Service
public class VerificationTokenService {

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private UserService userService;

    public VerificationToken createVerificationToken(String username){

        String token = UUID.randomUUID().toString();

        User user = userService.getUser(username);

        VerificationToken verificationToken = new VerificationToken(token,user);


        return verificationTokenRepository.save(verificationToken);

    }

    public String confirmToken(String token,String processId){

        VerificationToken verificationToken =verificationTokenRepository.findByVerificationToken(token);
        if (verificationToken == null) {
            userService.confirm(processId,"invalid");
            throw new ResponseStatusException(  HttpStatus.BAD_REQUEST,  "INVALID Token!" );
        }

        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            userService.confirm(processId,"expired");
            throw new ResponseStatusException(  HttpStatus.BAD_REQUEST,  "Token Expired!" );
        }

        user.setEnabled(true);
        user.setVerified(true);
        userService.saveToDB(user);

        userService.confirm(processId,"passed");

        return "Account activated successfully!";
    }




}
