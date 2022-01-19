package com.example.kr3.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class CGoodTest {

    CGood good = new CGood(UUID.randomUUID(), "Компьютер", 100.0, "Офисная техника");

    @Test
    void getId() {
        UUID id = good.getId();
        Assertions.assertTrue(id != null);
    }

    @Test
    void getName() {
        String name = good.getName();
        Assertions.assertTrue(name != null);
    }

    @Test
    void getCategory() {
        String category = good.getCategory();
        Assertions.assertTrue(category != null);
    }

    @Test
    void getCostOfGood() {
        double cost = good.getCost_of_good();
        Assertions.assertTrue(cost != 0);
    }
}