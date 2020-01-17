package com.sciencecenter.service;

import com.sciencecenter.model.*;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class MagazineSaveService implements JavaDelegate {

    @Autowired
    private MagazineService magazineService;

    @Autowired
    private UserMagazineService userMagazineService;

    @Autowired
    private UserService userService;

    @Autowired
    private FieldService fieldService;


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        String username = delegateExecution.getVariable("starter").toString();

        System.out.println(username);

        Magazine magazine = (Magazine) delegateExecution.getVariable("magazineObject");
        List<Field> magFields = (List<Field>)  delegateExecution.getVariable("magazineFields");
        List<PaymentType>  paymentTypes= (List<PaymentType>)  delegateExecution.getVariable("magazinePayments");


        if (magazine.getId()!=null){

            Magazine oldMag = magazineService.findById(magazine.getId());

            oldMag.setFields(new ArrayList<>());
            oldMag.setPaymentTypes(new ArrayList<>());

            magazineService.saveMagazine(oldMag);

            magazine.setUsers(oldMag.getUsers());
        }

        magazineService.checkMagazine(magazine);

        List<Field> fields = fieldService.compareAndAdd(magFields);

        User user = userService.getUser(username);

        UserMagazine userMagazine = new UserMagazine();
        userMagazine.setMagazine(magazine);
        userMagazine.setUser(user);
        userMagazine.setRole("main_editor");

        if(magazine.getId()!=null){
           userMagazine = userMagazineService.getByMagazineIdAndRole(magazine.getId(),"main_editor");
        }

        magazine.setFields(fields);
        magazine.setPaymentTypes(paymentTypes);

        magazineService.saveMagazine(magazine);

        userMagazineService.saveUserMagazine(userMagazine);
    }
}
