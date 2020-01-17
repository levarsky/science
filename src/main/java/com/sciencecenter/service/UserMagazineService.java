package com.sciencecenter.service;

import com.sciencecenter.model.Magazine;
import com.sciencecenter.model.User;
import com.sciencecenter.model.UserMagazine;
import com.sciencecenter.repository.UserMagazineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserMagazineService {

    @Autowired
    private UserMagazineRepository userMagazineRepository;

    public List<User> findUsersByMagazine(Magazine magazine, String role){

        List<UserMagazine> userMagazines =  userMagazineRepository.findByMagazineAndRole(magazine,role);

        List<User> users = new ArrayList<>();

        for (UserMagazine userMagazine:userMagazines){

            users.add(userMagazine.getUser());
        }

        return users;

    }

    public UserMagazine saveUserMagazine(UserMagazine userMagazine){
        return userMagazineRepository.save(userMagazine);
    }

    public UserMagazine getByMagazineIdAndRole(Long id,String role){
        if(!userMagazineRepository.findByMagazineIdAndRole(id,role).isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "UserMagazine doesn't exist!");
        return userMagazineRepository.findByMagazineIdAndRole(id,role).get();
    }

}
