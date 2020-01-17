package com.sciencecenter.model;

import java.util.HashMap;
import java.util.Map;

public class TaskDTO {

    String id;
    String name;
    Map<String,Object> variable = new HashMap<>();

    public TaskDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Object> getVariable() {
        return variable;
    }

    public void setVariable(Map<String, Object> variable) {
        this.variable = variable;
    }


}
