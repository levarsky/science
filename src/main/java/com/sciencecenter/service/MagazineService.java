package com.sciencecenter.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sciencecenter.model.*;
import com.sciencecenter.repository.MagazineRepository;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@Transactional
public class MagazineService {

    @Autowired
    private MagazineRepository magazineRepository;

    @Autowired
    private DiagramService diagramService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMagazineService userMagazineService;

    @Autowired
    private RoleService roleService;

    public String startMagazineProcess() {

        return diagramService.startProcess("add_magazine");

    }


    public void checkMagazine(Magazine magazine){

        if(magazine.getName().trim().equals(""))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name can't be empty!");

        if(magazineRepository.existsByName(magazine.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name exists!");
        }

        if(!Pattern.compile("^[0-9]{4}-[0-9]{3}[0-9xX]$").matcher(magazine.getISSN()).find())
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong Format!");

        if(magazine.getId()==null){
            if(magazineRepository.existsByISSN(magazine.getISSN())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ISSN Exists!");
            }
        }else{
            if(magazineRepository.existsByISSN(magazine.getISSN())){
               if (!magazineRepository.findByISSN(magazine.getISSN()).getId().equals(magazine.getId())){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ISSN Exists!");
                }

            }
        }


        if(magazine.getPaymentTypes().isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Minium one PaymentType!");


    }

    public Magazine saveMagazine(Magazine magazine){


        return magazineRepository.save(magazine);

    }

    public Magazine findByISSN(String issn){



        return magazineRepository.findByISSN(issn);

    }

    public Magazine findById(Long id){
        Optional<Magazine> magazineOptional = magazineRepository.findById(id);
        if (!magazineOptional.isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Magazine id doesn't exist!");
        return magazineOptional.get();
    }


    public void submitMagazine(String processId, Magazine magazine) {

        System.out.println(magazine.getISSN());
        System.out.println(magazine.getPaymentTypes().size());

        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> map = mapper.convertValue(magazine, new TypeReference<HashMap<String, Object>>() {});

        Task task= diagramService.getTask(processId,0,"mag");

        diagramService.setVariable(processId,"magazineObject",magazine);
        diagramService.setVariable(processId,"magazineFields",magazine.getFields());
        diagramService.setVariable(processId,"magazinePayments",magazine.getPaymentTypes());

        diagramService.submitTask(task.getId(),map);


    }

    public UserListDTO getMagazineUsers(String issn){

        Magazine magazine = magazineRepository.findByISSN(issn);

        UserListDTO userListDTO = new UserListDTO();

        userListDTO.setEditors(userService.getUsers("editor",magazine.getFields()));
        userListDTO.setReviewers(userService.getUsers("reviewer",magazine.getFields()));

        return userListDTO;

    }

    public void submitMagazineUsers(String processId,UserListDTO userListDTO){

        HashMap<String, Object> map = new HashMap<>();
        Task task= diagramService.getTask(processId,0,"editor_reviewer");

        diagramService.setVariable(processId,"userListMagazine",userListDTO);
        diagramService.submitTask(task.getId(),map);


    }

    public List<TaskDTO> getMagazines(){

        List<TaskDTO> taskDTOS = diagramService.getMagazineTasks("admin_approve","camunda-admin");

        return taskDTOS;

    }

    public UserListDTO getERMagazine(String issn){

        Magazine magazine = findByISSN(issn);

        UserListDTO userListDTO = new UserListDTO();

        userListDTO.setEditors(userMagazineService.findUsersByMagazine(magazine,"editor"));
        userListDTO.setReviewers(userMagazineService.findUsersByMagazine(magazine,"reviewer"));
        userListDTO.setMainEditor(userMagazineService.findUsersByMagazine(magazine,"main_editor").get(0));

        return userListDTO;

    }

    public List<Field> getFields(String issn){
        Magazine magazine = findByISSN(issn);

        return magazine.getFields();
    }

    public List<PaymentType> getPaymentType(String issn){
        Magazine magazine = findByISSN(issn);

        return magazine.getPaymentTypes();
    }


    public void approveMagazine(String taskId,Magazine approveMagazine){
        HashMap<String, Object> map = new HashMap<>();
        System.out.println(approveMagazine.isActive());
        map.put("approveMagazine",approveMagazine.isActive());

        diagramService.submitTask(taskId,map);

        Magazine magazine = magazineRepository.findByISSN(approveMagazine.getISSN());
        magazine.setActive(approveMagazine.isActive());
        magazineRepository.save(magazine);
    }

    public List<TaskDTO> getUserMagazineTasks(){

        List<TaskDTO> taskDTOS = diagramService.getMagazineTasks("mag","editor");
        List<TaskDTO> taskDTOU = diagramService.getMagazineTasks("editor_reviewer","editor");

        taskDTOS.addAll(taskDTOU);

        return taskDTOS;

    }

    public List<TaskDTO> getAllUserMagazines(){

        User user = userService.getUser(diagramService.getAuthUsername());

        List<UserMagazine> userMagazines = user.getMagazines();

        Role role = roleService.findByName("camunda-admin");

        boolean isAdmin = false;

        if(user.getRoles().contains(role)){
            userMagazines = userMagazineService.getAll();
            isAdmin = true;
        }

        List<TaskDTO> magazines = new ArrayList<>();
        ArrayList<Magazine> mags = new ArrayList<>();

        for(UserMagazine um : userMagazines){



            TaskDTO taskDTO = new TaskDTO();
            taskDTO.setName("all");
            taskDTO.getVariable().put("magazine",um.getMagazine());

            if (um.getMagazine().isActive() && !isAdmin){
                if (!mags.contains(um.getMagazine())) {
                    mags.add(um.getMagazine());
                    magazines.add(taskDTO);
                }
            }else if (isAdmin){
                if (!mags.contains(um.getMagazine())) {
                    mags.add(um.getMagazine());
                    magazines.add(taskDTO);
                }
            }

        }

        return magazines;
    }


    public UserListDTO getAddedUsers(String issn){

        Magazine magazine = magazineRepository.findByISSN(issn);

        return getERMagazine(issn);

    }



}
