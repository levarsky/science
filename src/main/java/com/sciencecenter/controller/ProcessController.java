package com.sciencecenter.controller;

import com.sciencecenter.model.FormDTO;
import com.sciencecenter.service.DiagramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;

@Controller
@RequestMapping("process")
public class ProcessController {

    @Autowired
    private DiagramService diagramService;

    @RequestMapping(value = "/getProcessId",method = RequestMethod.GET)
    public ResponseEntity<?> getProcessId(@RequestParam String taskId){
        return new ResponseEntity<>(Collections.singletonMap("processId",diagramService.getProcessIdFromTaskId(taskId)) ,HttpStatus.OK);
    }

    @RequestMapping(value="/submitTask",method = RequestMethod.POST)
    public ResponseEntity<?> submitTask(@RequestBody Object object,@RequestParam String taskId, @RequestParam String varName){
        diagramService.submitFormFields(object,taskId,varName);
        return new ResponseEntity<>(HttpStatus.OK);
    }




}
