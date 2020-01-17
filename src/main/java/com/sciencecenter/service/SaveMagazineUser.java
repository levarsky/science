package com.sciencecenter.service;

import com.sciencecenter.model.Magazine;
import com.sciencecenter.model.User;
import com.sciencecenter.model.UserListDTO;
import com.sciencecenter.model.UserMagazine;
import com.sciencecenter.repository.UserMagazineRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class SaveMagazineUser implements JavaDelegate {

    @Autowired
    private MagazineService magazineService;

    @Autowired
    private UserMagazineRepository userMagazineRepository;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        delegateExecution.setVariable("userMagPass",false);

        Magazine magazine = magazineService.findByISSN(delegateExecution.getVariable("issn").toString());

        List<UserMagazine> userMagazineList = new ArrayList<>();

        UserListDTO userListDTO = (UserListDTO) delegateExecution.getVariable("userListMagazine");

        List<User> editors = userListDTO.getEditors();
        List<User> reviewers = userListDTO.getReviewers();

        userMagazineRepository.deleteByMagazineIdAndRole(magazine.getId(),"reviewer");
        userMagazineRepository.deleteByMagazineIdAndRole(magazine.getId(),"editor");

        if (reviewers.size() < 2)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Minimum 2 reviewers!");


        for(int i = 0 ;i<editors.size();i++) {
            UserMagazine ed = new UserMagazine();
            ed.setRole("editor");
            ed.setUser(editors.get(i));
            ed.setMagazine(magazine);
            userMagazineRepository.save(ed);
        }


        for(int i = 0 ;i<reviewers.size();i++){
            UserMagazine  temp = new UserMagazine();
            temp.setRole("reviewer");
            temp.setUser(reviewers.get(i));
            temp.setMagazine(magazine);
            userMagazineRepository.save(temp);
        }

        delegateExecution.setVariable("magazineObject",magazine);

        delegateExecution.setVariable("userMagPass",true);
    }
}
