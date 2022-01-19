package com.example.kr3.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

class CUserTest {

    CUser user = new CUser(UUID.randomUUID(),"Maxim8567","Максим", "м", LocalDate.parse("1999-05-23", DateTimeFormatter.ofPattern("yyyy-MM-dd")));

    @Test
    void getSex() {
        String sex = user.getSex();
        Assertions.assertTrue(sex != null);
    }

    @Test
    void getId() {
        UUID id = user.getId();
        Assertions.assertTrue(id != null);
    }

    @Test
    void getDateOfBirth() {
        LocalDate date = user.getDateOfBirth();
        Assertions.assertTrue(date != null);
    }

    @Test
    void getAge() {
        int age =  LocalDate.now().getYear()-user.dateOfBirth.getYear();
        Assertions.assertTrue(age != 0);
    }

    @Test
    void getName() {
        String name = user.getName();
        Assertions.assertTrue(name != null);
    }

    @Test
    void getLogin() {
        String login = user.getLogin();
        Assertions.assertTrue(login != null);
    }
}