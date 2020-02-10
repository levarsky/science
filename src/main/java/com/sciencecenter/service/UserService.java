package com.sciencecenter.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sciencecenter.model.*;

import com.sciencecenter.repository.UserRepository;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    @Autowired
    private DiagramService diagramService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    public void signUp(String taskId, User user){

        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> map = mapper.convertValue(user, new TypeReference<HashMap<String, Object>>() {});

        Task task= diagramService.getTask(taskId);
        diagramService.setVariable(task.getProcessInstanceId(),"signUp",user);
        diagramService.submitTask(taskId,map);
    }


    public void checkAuth(String user){
        if (!userRepository.existsByUsername(user)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong credentials!");
        }

        Optional<User> userRep = userRepository.findByUsername(user);

        if (!userRep.get().getVerified()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not verifed!");
        }
    }


    public void checkUser(User user ){
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username is already taken!");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is registered!");
        }
    }

    public void saveToDB(User user){
        userRepository.save(user);
    }

    public void saveToDBWithRole(User user,String roleName){

        Role role = roleService.findByName(roleName);
        user.getRoles().add(role);

        saveToDB(user);
    }

    public User getUser(String username){
        Optional<User> user = userRepository.findByUsername(username);

        if (!user.isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User doesn't exist!");

        return user.get();
    }

    public void confirm(String processId,String tokenValid){

        HashMap<String,Object> map = new HashMap<>();
        map.put("tokenValid",tokenValid);

        Task task= diagramService.getTask(processId,0,"verify_email");

        diagramService.setVariable(processId,"verifiedUser",null);
        diagramService.submitTask(task.getId(),map);
    }

    public void approveReviewer(String id , String approve){

        HashMap<String,Object> map = new HashMap<>();
        map.put("approve",approve);

        diagramService.submitTask(id,map);

    }

    public User getUserAuth(){

        return getUser(diagramService.getAuthUsername());
    }

    public List<User> getUsers(String roleName, List<Field> fields){

        Role role = roleService.findByName(roleName);

        User authUser = getUser(diagramService.getAuthUsername());

        List<User> users = new ArrayList<>();

        for(Field f:fields){

            System.out.println(f.getName());

            users.addAll(userRepository.findByRolesAndFieldsAndUsernameIsNot(role,f,authUser.getUsername()));

        }

        users = users.stream().distinct().collect(Collectors.toList());

        return users;
    }

    public List<User> getUsers(String roleName, Magazine magazine){

        Role role = roleService.findByName(roleName);

        List<User> users = new ArrayList<>();

        users.addAll(userRepository.findByRolesAndMagazines(role,magazine));

        return users;
    }


    public List<TaskDTO> getReviewers(){

        diagramService.authorize("camunda-admin");

        return  diagramService.getGroupProcess("approve_reviewer","camunda-admin");

    }

    public User getUser(){

        return userRepository.getOne(1L);
    }

    public List<String> getGroups() {

        return diagramService.getGroups();
    }
}
