package com.sciencecenter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.ibatis.annotations.One;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Magazine implements Serializable {

    private static final long serialVersionUID = 2642114970786261547L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 30)
    private String name;
    private String ISSN;
    private boolean isActive;

    private String payment;


    @JsonIgnore
    @OneToMany(mappedBy="magazine", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<UserMagazine> users = new ArrayList<>();


    @JsonIgnore
    @OneToMany(mappedBy = "magazine",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Content> issues = new ArrayList<>();


    @ManyToMany(fetch=FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinTable(joinColumns = @JoinColumn(name = "MAGAZINE_ID"), inverseJoinColumns = @JoinColumn(name = "FIELD_ID"))
    private List<Field> fields = new ArrayList<>();


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(joinColumns = @JoinColumn(name = "MAGAZINE_ID"), inverseJoinColumns = @JoinColumn(name = "PAYMENT_TYPE_ID"))
    private List<PaymentType> paymentTypes = new ArrayList<>();

    public Magazine() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getISSN() {
        return ISSN;
    }

    public void setISSN(String ISSN) {
        this.ISSN = ISSN;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<UserMagazine> getUsers() {
        return users;
    }

    public void setUsers(List<UserMagazine> users) {
        this.users = users;
    }

    public List<PaymentType> getPaymentTypes() {
        return paymentTypes;
    }

    public void setPaymentTypes(List<PaymentType> paymentTypes) {
        this.paymentTypes = paymentTypes;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public List<Content> getIssues() {
        return issues;
    }

    public void setIssues(List<Content> issues) {
        this.issues = issues;
    }
}
