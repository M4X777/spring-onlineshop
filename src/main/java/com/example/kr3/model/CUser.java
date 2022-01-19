package com.example.kr3.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class CUser {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    public UUID id;

    @Column(name = "sex")
    public String sex;

    @Column(name = "name")
    public String name;

    @Column(name = "date_of_birth", columnDefinition = "DATE")
    public LocalDate dateOfBirth;

    @Column(name = "login")
    public String login;

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER,
            cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIdentityReference(alwaysAsId = true)
    public List<COrder> orders = new ArrayList<>();

    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }

    public UUID getId() {
        return id;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(LocalDate dateOfBirth) {
        int age = CUser.getNewAge(dateOfBirth);
        if (age<150 && age>=8)
            this.dateOfBirth = dateOfBirth;
    }
    public int getAge() {
        LocalDate now = LocalDate.now();
        if (this.dateOfBirth == null)
            return 0;
        else
             return now.getYear()-this.dateOfBirth.getYear();
    }

    public static int getNewAge(LocalDate date) {
        LocalDate now = LocalDate.now();
        if (date==null)
            return now.getYear();
        return now.getYear()-date.getYear();
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getLogin()
    {
        return login;
    }
    public void setLogin(String login)
    {
        if (login.length()<=50)
            this.login = login;
    }

    public List<COrder> getOrders()
    {
        return orders;
    }

    public CUser() {
        id = UUID.randomUUID();
        sex = "";
        name = "";
        dateOfBirth = LocalDate.now();
        login = "";
    }
    public CUser(String login, LocalDate dateOfBirth, String sex) {
        this(UUID.randomUUID(), sex, login, dateOfBirth);
    }

    public CUser(UUID id, String sex, String login, LocalDate dateOfBirth) {
        this.id = id;
        setLogin(login);
        setDateOfBirth(dateOfBirth);
        setSex(sex);
    }

    public CUser(UUID id, String login, LocalDate dateOfBirth, String sex) {
        this.id = id;
        setLogin(login);
        setDateOfBirth(dateOfBirth);
        setSex(sex);
    }

    public CUser(UUID id, String login, String name, String sex, LocalDate dateOfBirth) {
        this.id = id;
        setName(name);
        setLogin(login);
        setDateOfBirth(dateOfBirth);
        setSex(sex);
        getOrders();
    }

    public CUser(UUID id, String login, String name, String sex, LocalDate dateOfBirth, List<COrder> orders) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
        this.orders = orders;
    }
}
