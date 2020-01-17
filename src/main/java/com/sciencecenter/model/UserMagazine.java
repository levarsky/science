package com.sciencecenter.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class UserMagazine implements Serializable {


    @EmbeddedId
    private UserMagazineId userMagazineId = new UserMagazineId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private User user;


    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("magazineId")
    private Magazine magazine;

    private String role;

    public UserMagazine() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Magazine getMagazine() {
        return magazine;
    }

    public void setMagazine(Magazine magazine) {
        this.magazine = magazine;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
