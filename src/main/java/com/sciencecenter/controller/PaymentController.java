package com.sciencecenter.controller;

import com.sciencecenter.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collections;
@Controller
@RequestMapping("payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;


    @RequestMapping(value="/all",method = RequestMethod.GET)
    public ResponseEntity<?> magazineProcess() {
        return new ResponseEntity<>(paymentService.getAllTypes(), HttpStatus.OK);
    }

}
