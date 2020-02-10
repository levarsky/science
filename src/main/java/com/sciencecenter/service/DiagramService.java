package com.sciencecenter.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sciencecenter.model.FormDTO;
import com.sciencecenter.model.Magazine;
import com.sciencecenter.model.TaskDTO;
import com.sciencecenter.model.User;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.authorization.Authorization;
import org.camunda.bpm.engine.authorization.Groups;
import org.camunda.bpm.engine.authorization.Permissions;
import org.camunda.bpm.engine.authorization.Resources;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class DiagramService {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private FormService formService;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private AuthorizationService authorizationService;


    public String startProcess(String processName){

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processName);

        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().get(0);

        System.out.println(taskService.getIdentityLinksForTask(task.getId()));


        System.out.println(task.getId()+" "+ task.getName());

        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();
        for(FormField fp : properties) {
            System.out.println(fp.getId());
        }

        //createUser();

        return processInstance.getId();

    }

    public FormDTO startProcessAndGetForm(String processName){

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processName);
        System.out.println(taskService.createTaskQuery().processInstanceId(processInstance.getId()).list());
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().get(0);
        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();

        FormDTO formDTO = new FormDTO();

        formDTO.setFormFields(properties);
        formDTO.setProcessInstanceId(processInstance.getId());
        formDTO.setTaskId(task.getId());

        //createUser();

        return formDTO;

    }

    public FormDTO getFormProcessIdTaskName(String processId,String taskName){

        Task task = getTask(processId,0,taskName);

        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();

        FormDTO formDTO = new FormDTO();

        formDTO.setFormFields(properties);
        formDTO.setProcessInstanceId(processId);
        formDTO.setTaskId(task.getId());

        return formDTO;

    }

    public void submitFormFields(Object object,String taskId,String varName){

        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> map = mapper.convertValue(object, new TypeReference<HashMap<String, Object>>() {});

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        setVariable(task.getProcessInstanceId(),varName,object);

        formService.submitTaskForm(taskId, map);
    }



    public boolean authorize(String group){
        List<String> groupIds = identityService.getCurrentAuthentication().getGroupIds();

        if(!groupIds.contains(group))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not allowed!");

        return true;
    }

    public Task getTask(String taskId){

        return taskService.createTaskQuery().taskId(taskId).singleResult();
    }

    public Task getTask(String processInstanceId,int i,String definitionKey){
        System.out.println("GET TASK "+ taskService.createTaskQuery().taskDefinitionKey(definitionKey).processInstanceId(processInstanceId).list().get(i));
        Task task = taskService.createTaskQuery().taskDefinitionKey(definitionKey).processInstanceId(processInstanceId).list().get(i);
        return task ;
    }

    public void setVariable(String processInstanceId,String varName,Object object){
        runtimeService.setVariable(processInstanceId, varName, object);
    }

    public void submitTask(String taskId,HashMap map){
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        formService.submitTaskForm(taskId, map);
    }

    public void completeTask(String taskId,HashMap map){
        taskService.complete(taskId,map);
    }

    public List<TaskDTO> getGroupProcess(String taskDef,String candGroup) {

        List<TaskDTO> tasks = new ArrayList<>();

        System.out.println(identityService.getCurrentAuthentication().getUserId()+" " +identityService.getCurrentAuthentication().getGroupIds());

        List<Task> taskAD = taskService.createTaskQuery().taskDefinitionKey(taskDef).taskCandidateGroup(candGroup).list();

        for (Task t: taskAD){
            TaskFormData tfd = formService.getTaskFormData(t.getId());
            List<FormField> properties = tfd.getFormFields();
            for(FormField fp : properties) {
                System.out.println(fp.getId());
            }


            TaskDTO taskDTO = new TaskDTO();

            taskDTO.setId(t.getId());
            taskDTO.setName(t.getDescription());

            Map<String,Object> map =  runtimeService.getVariables(t.getProcessInstanceId());

            User user = (User) map.get("signUp");
            taskDTO.getVariable().put("user",user);

            tasks.add(taskDTO);
        }


        return tasks;

    }

    public List<TaskDTO> getMagazineTasks(String taskDef,String candGroup) {

        List<TaskDTO> tasks = new ArrayList<>();

        System.out.println(identityService.getCurrentAuthentication().getUserId()+" " +identityService.getCurrentAuthentication().getGroupIds());

        List<String> list =  identityService.getCurrentAuthentication().getGroupIds();

        List<Task> taskAD;

        if(list.contains("camunda-admin")){
            taskAD = taskService.createTaskQuery().taskDefinitionKey(taskDef).taskCandidateGroup(candGroup).list();
        }else{
            String username = identityService.getCurrentAuthentication().getUserId();
            System.out.println(username);
            taskAD = taskService.createTaskQuery().taskDefinitionKey(taskDef).taskAssignee(username).list();
        }

        for (Task t: taskAD){

            TaskDTO taskDTO = new TaskDTO();
            taskDTO.setId(t.getId());
            taskDTO.setName(t.getName());

            Map<String,Object> map =  runtimeService.getVariables(t.getProcessInstanceId());

            Magazine magazine = (Magazine) map.get("magazineObject");



            if (magazine!=null){
                magazine.setFields(new ArrayList<>());
                taskDTO.getVariable().put("magazine",magazine);
                taskDTO.getVariable().put("processId",t.getProcessInstanceId());
                tasks.add(taskDTO);
            }

        }

        return tasks;

    }




    public void auth (){
        System.out.println(identityService.getCurrentAuthentication().getUserId()+" " +identityService.getCurrentAuthentication().getGroupIds());
    }

    public void createUser(){

//        Group group =identityService.newGroup("editor");
//        group.setName("editor");
//        group.setType(Groups.GROUP_TYPE_WORKFLOW);
//        identityService.saveGroup(group);
//
//        Group group2 =identityService.newGroup("reviewer");
//        group2.setName("reviewer");
//        group2.setType(Groups.GROUP_TYPE_WORKFLOW);
//        identityService.saveGroup(group2);
//
//        Group group1 =identityService.newGroup("user");
//        group1.setName("user");
//        group1.setType(Groups.GROUP_TYPE_WORKFLOW);
//        identityService.saveGroup(group1);
//
////        identityService.createMembership("pera","user");
        Authorization authorization = authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
//
        authorization.setGroupId("author");
        authorization.addPermission(Permissions.CREATE_INSTANCE);
        authorization.setResource(Resources.PROCESS_DEFINITION);
        authorization.setResourceId("*");
//
        authorizationService.saveAuthorization(authorization);
//
        Authorization authorization2 = authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);

        authorization2.setGroupId("author");
        authorization2.addPermission(Permissions.CREATE);
        authorization2.addPermission(Permissions.READ);
        authorization2.addPermission(Permissions.UPDATE);
        authorization2.setResource(Resources.PROCESS_INSTANCE);
        authorization2.setResourceId("*");

        authorizationService.saveAuthorization(authorization2);

//        Authorization authorization2 = authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
//
//        authorization2.setGroupId("reviewer");
//        authorization2.addPermission(Permissions.ACCESS);
//        authorization2.setResource(Resources.APPLICATION);
//        authorization2.setResourceId(Authorization.ANY);
//
//        authorizationService.saveAuthorization(authorization2);
//
//
//        Authorization authorization3 = authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
//
//        authorization3.setGroupId("user");
//        authorization3.addPermission(Permissions.ACCESS);
//        authorization3.setResource(Resources.APPLICATION);
//        authorization3.setResourceId(Authorization.ANY);
//
//        authorizationService.saveAuthorization(authorization3);


    }

    public Task getTaskFromId(String taskID){
        return taskService.createTaskQuery().taskId(taskID).list().get(0);
    }

    public String getProcessIdFromTaskId(String taskId){
        return taskService.createTaskQuery().taskId(taskId).list().get(0).getProcessInstanceId();
    }

    public String getAuthUsername() {
        return identityService.getCurrentAuthentication().getUserId();
    }

    public List<String> getGroups() {
        System.out.println(identityService.getCurrentAuthentication().getGroupIds());
        return identityService.getCurrentAuthentication().getGroupIds();
    }
}
