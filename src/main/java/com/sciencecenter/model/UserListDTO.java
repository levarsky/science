package com.sciencecenter.model;

import java.io.Serializable;
import java.util.List;

public class UserListDTO implements Serializable {

    private static final long serialVersionUID = -1401803204518099076L;

    private List<User> editors;
    private List<User> reviewers;
    private User mainEditor;

    public UserListDTO() {
    }

    public User getMainEditor() {
        return mainEditor;
    }

    public void setMainEditor(User mainEditor) {
        this.mainEditor = mainEditor;
    }

    public List<User> getEditors() {
        return editors;
    }

    public void setEditors(List<User> editors) {
        this.editors = editors;
    }

    public List<User> getReviewers() {
        return reviewers;
    }

    public void setReviewers(List<User> reviewers) {
        this.reviewers = reviewers;
    }
}
