package com.sciencecenter.controller;

import com.sciencecenter.model.Magazine;
import com.sciencecenter.model.User;
import com.sciencecenter.model.UserListDTO;
import com.sciencecenter.service.FieldService;
import com.sciencecenter.service.MagazineService;
import com.sciencecenter.service.UserService;
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
@RequestMapping("magazine")
public class MagazineController {

    @Autowired
    private MagazineService magazineService;

    @Autowired
    private FieldService fieldService;

    @RequestMapping(value="/magazineProcess",method = RequestMethod.GET)
    public ResponseEntity<?> magazineProcess() {
        return new ResponseEntity<>(Collections.singletonMap("processId",magazineService.startMagazineProcess()), HttpStatus.OK);
    }


    @RequestMapping(value="/chooseMagazineProcess",method = RequestMethod.GET)
    public ResponseEntity<?> chooseMagazineProcess() {
        return new ResponseEntity<>(magazineService.startChooseMagazineProcess(), HttpStatus.OK);
    }

    @RequestMapping(value="/issueDetailsForm",method = RequestMethod.GET)
    public ResponseEntity<?> issueDetailsForm(@RequestParam(value = "processId") String processId) {
        return new ResponseEntity<>(magazineService.getIssueDetailsForm(processId), HttpStatus.OK);
    }

    @RequestMapping(value = "/submitMagazine",method = RequestMethod.POST)
    public ResponseEntity<String> submitMagazine(@RequestParam(value = "processId") String processId , @RequestBody Magazine magazine) {
        magazineService.submitMagazine(processId,magazine);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @RequestMapping(value = "/submitUserMagazine",method = RequestMethod.POST)
    public ResponseEntity<?> submitUserMagazine(@RequestParam(value = "processId") String processId , @RequestBody UserListDTO userListDTO) {
        magazineService.submitMagazineUsers(processId,userListDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/getMagazineUsers",method = RequestMethod.GET)
    public ResponseEntity<?> getMagazineUsers(@RequestParam(value = "issn") String issn) {
        return new ResponseEntity<>(magazineService.getMagazineUsers(issn),HttpStatus.OK);
    }

    @RequestMapping(value = "/getMagazine",method = RequestMethod.GET)
    public ResponseEntity<?> getMagazine(@RequestParam(value = "issn") String issn) {
        return new ResponseEntity<>(magazineService.findByISSN(issn),HttpStatus.OK);
    }

    @RequestMapping(value = "/getMagazines",method = RequestMethod.GET)
    public ResponseEntity<?> getMagazinesForApprove() {
        return new ResponseEntity<>(magazineService.getMagazines(),HttpStatus.OK);
    }

    @RequestMapping(value = "/approveMagazine",method = RequestMethod.PUT)
    public ResponseEntity<String> approveMagazine(@RequestParam String taskId , @RequestBody Magazine magazine) {
        magazineService.approveMagazine(taskId,magazine);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/getERMagazine",method = RequestMethod.GET)
    public ResponseEntity<UserListDTO> approveMagazine(@RequestParam String issn) {
        return new ResponseEntity<>(magazineService.getERMagazine(issn),HttpStatus.OK);
    }

    @RequestMapping(value = "/getFields",method = RequestMethod.GET)
    public ResponseEntity<?> getFields(@RequestParam String issn) {
        return new ResponseEntity<>(magazineService.getFields(issn),HttpStatus.OK);
    }

    @RequestMapping(value = "/getPaymentType",method = RequestMethod.GET)
    public ResponseEntity<?> getPaymentType(@RequestParam String issn) {
        return new ResponseEntity<>(magazineService.getPaymentType(issn),HttpStatus.OK);
    }

    @RequestMapping(value = "/getUserMagazinesTasks",method = RequestMethod.GET)
    public ResponseEntity<?> getUserMagazinesTasks() {
        return new ResponseEntity<>(magazineService.getUserMagazineTasks(),HttpStatus.OK);
    }

    @RequestMapping(value = "/getAllUserMagazines",method = RequestMethod.GET)
    public ResponseEntity<?> getAllUserMagazines() {
        return new ResponseEntity<>(magazineService.getAllUserMagazines(),HttpStatus.OK);
    }

    @RequestMapping(value = "/getAllFields",method = RequestMethod.GET)
    public ResponseEntity<?> getAllFields() {
        return new ResponseEntity<>(fieldService.getAllFields(),HttpStatus.OK);
    }

    @RequestMapping(value = "/getAddedUsers",method = RequestMethod.GET)
    public ResponseEntity<?> getAddedUsers(@RequestParam String issn) {
        return new ResponseEntity<>(magazineService.getAddedUsers(issn),HttpStatus.OK);
    }

}
