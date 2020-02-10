package com.sciencecenter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @JsonIgnore
    @OneToMany(mappedBy="content", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Author> authors=new ArrayList<>();

    private String abstractValue;


    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(joinColumns = @JoinColumn(name = "CONTENT_ID"), inverseJoinColumns = @JoinColumn(name = "KEY_WORD_ID"))
    private Set<KeyWord> keyWords = new HashSet<>();


    @OneToOne
    private Field field;


    @ManyToOne(fetch = FetchType.LAZY)
    private Magazine magazine;

    private byte pdf;

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

    public String getAbstractValue() {
        return abstractValue;
    }

    public void setAbstractValue(String abstractValue) {
        this.abstractValue = abstractValue;
    }

    public Set<KeyWord> getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(Set<KeyWord> keyWords) {
        this.keyWords = keyWords;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public byte getPdf() {
        return pdf;
    }

    public void setPdf(byte pdf) {
        this.pdf = pdf;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public Magazine getMagazine() {
        return magazine;
    }

    public void setMagazine(Magazine magazine) {
        this.magazine = magazine;
    }
}
