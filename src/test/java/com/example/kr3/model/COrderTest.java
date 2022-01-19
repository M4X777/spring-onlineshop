package com.example.kr3.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class COrderTest {

    CUser owner = new CUser(UUID.randomUUID(),"Maxim8567","Максим", "м", LocalDate.parse("1999-05-23", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    COrder order = new COrder(UUID.randomUUID(), owner, new ArrayList<>(), LocalDate.parse("2021-05-23", DateTimeFormatter.ofPattern("yyyy-MM-dd")));

    @Test
    void getId() {
        UUID id = order.getId();
        Assertions.assertTrue(id != null);
    }

    @Test
    void getOwner() {
        CUser thisOwner = order.getOwner();
        Assertions.assertTrue(thisOwner != null);
    }

    @Test
    void getOwnerName() {
        String ownerName = order.getOwner().getName();
        Assertions.assertTrue(ownerName != null);
    }

    @Test
    void getDateOfPurchase() {
        LocalDate date = order.getDate_of_purchase();
        Assertions.assertTrue(date != null);
    }

    @Test
    void getGoods() {
        List<CGood> goods = order.getGoods();
        Assertions.assertTrue(goods != null);
    }

    @Test
    void addGood() {
        List<CGood> goods = order.getGoods();
        goods.add(new CGood());
        Assertions.assertTrue(goods.size() > 0);
    }
}