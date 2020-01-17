package com.sciencecenter.service;

import com.sciencecenter.model.Field;
import com.sciencecenter.repository.FieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FieldService {

    @Autowired
    private FieldRepository fieldRepository;

    public List<Field> compareAndAdd(List<Field> fields){

        if(fields.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Must choose one field!");
        }

        List<Field> newFields = new ArrayList<>();

        for (Field f:fields){

            if (f.getName().trim().equals(""))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Field is empty!");

            f.setName(f.getName().toLowerCase());

            Optional<Field> tempField = fieldRepository.findByName(f.getName());

            if (tempField.isPresent()){
                Field extField = tempField.get();
                newFields.add(extField);
            }else{
                Field newField = fieldRepository.save(f);
                newFields.add(newField);
            }

        }
        return newFields;
    }

    public List<Field> getAllFields(){
        return fieldRepository.findAll();
    }

}
