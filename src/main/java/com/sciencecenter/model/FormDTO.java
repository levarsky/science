package com.sciencecenter.model;

import org.camunda.bpm.engine.form.FormField;

import java.io.Serializable;
import java.util.List;

public class FormDTO implements Serializable {

    String taskId;
    List<FormField> formFields;
    String processInstanceId;
    Object object;

    public FormDTO() {
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public List<FormField> getFormFields() {
        return formFields;
    }

    public void setFormFields(List<FormField> formFields) {
        this.formFields = formFields;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
