package com.sciencecenter.controller;

import com.sciencecenter.model.User;
import com.sciencecenter.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;

@Controller
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private DiagramService diagramService;

    @Autowired
    private VerificationTokenService verificationTokenService;

    @Autowired
    private UserService userService;

    @Autowired
    private FieldService fieldService;

    @RequestMapping(value="/check",method = RequestMethod.GET)
    public ResponseEntity<?> check(@RequestParam("username") String username) {
        userService.checkAuth(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value="/regProcess",method = RequestMethod.GET)
    public ResponseEntity<?> runProcess() {

        return new ResponseEntity<>(Collections.singletonMap("processId",diagramService.startProcess("Registration_process")), HttpStatus.OK);
    }

    @RequestMapping(value = "/signUp",method = RequestMethod.POST)
    public ResponseEntity<String> userSignUp(@RequestParam(value = "processId") String processId , @RequestBody User user) {
        userService.signUp(processId,user);
        return new ResponseEntity<>( HttpStatus.OK);
    }


    @RequestMapping(value = "/confirm",method = RequestMethod.GET)
    public ResponseEntity<String> confirm(@RequestParam(value = "process") String process,@RequestParam(value = "token") String token){
        return new ResponseEntity<>( verificationTokenService.confirmToken(token,process),HttpStatus.OK);
    }

    @RequestMapping(value = "/getAllFields",method = RequestMethod.GET)
    public ResponseEntity<?> getAllFields() {
        return new ResponseEntity<>(fieldService.getAllFields(),HttpStatus.OK);
    }

}
