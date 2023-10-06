package com.nikonenko.springBookShop.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="person")
public class Person {
    @Id
    @Column(name = "id_user")
    private Integer id_user;

    @OneToOne(optional = true)
    @MapsId
    @JoinColumn(name = "id_user")
    private User user;

    @Column(name = "name_person")
    private String name;

    @Column(name = "gender")
    private String gender;

    public Person(){}

    public Person(User user, String name, String gender) {
        this.id_user = user.getId_user();
        this.user = user;
        this.name = name;
        this.gender = gender;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getId_user() {
        return id_user;
    }

    public void setId_user(Integer id_user) {
        this.id_user = id_user;
    }
}
