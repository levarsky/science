package com.sciencecenter.controller;

import com.sciencecenter.model.Field;
import com.sciencecenter.model.User;
import com.sciencecenter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/auth",method = RequestMethod.GET)
    public ResponseEntity<?> auth(){
        return new ResponseEntity<>(Collections.singletonMap("message","Authenticated!") ,HttpStatus.OK);
    }

    @RequestMapping(value = "/getGroup",method = RequestMethod.GET)
    public ResponseEntity<?> getGroups(){
        return new ResponseEntity<>(userService.getGroups(),HttpStatus.OK);
    }


    @RequestMapping(value = "/getRevRequest",method = RequestMethod.GET)
    public ResponseEntity<?> getRevRequest(){
        return new ResponseEntity<>(userService.getReviewers(),HttpStatus.OK);
    }

    @RequestMapping(value = "/confirmReviewer",method = RequestMethod.GET)
    public ResponseEntity<String> confirmReviewer(@RequestParam(value = "taskId") String taskId,@RequestParam(value = "approve") String approve){
        userService.approveReviewer(taskId,approve);
        return new ResponseEntity<>( HttpStatus.OK);
    }

    @RequestMapping(value = "/getUser",method = RequestMethod.GET)
    public ResponseEntity<?> getUser(){
        return new ResponseEntity<>(userService.getUser(),HttpStatus.OK);
    }



}
