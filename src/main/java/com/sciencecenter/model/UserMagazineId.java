package com.sciencecenter.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserMagazineId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name="user_id")
    private Long userId;
    @Column(name="magazine_id")
    private Long magazineId;

    public UserMagazineId() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getMagazineId() {
        return magazineId;
    }

    public void setMagazineId(Long magazineId) {
        this.magazineId = magazineId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId,magazineId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        UserMagazineId userMagazineId = (UserMagazineId) obj;

        return Objects.equals(userId,userMagazineId.getUserId()) &&
                Objects.equals(magazineId,userMagazineId.getMagazineId());
    }
}
