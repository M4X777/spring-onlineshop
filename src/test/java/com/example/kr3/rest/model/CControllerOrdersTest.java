package com.example.kr3.rest.model;

import com.example.kr3.model.COrder;
import com.example.kr3.repositories.IRepositoryOrders;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CControllerOrdersTest {

    @Autowired
    private IRepositoryOrders repositoryOrders;

    @Test
    void getAllTest()
    {
        List<COrder> allOrders = repositoryOrders.findAll();

        Assertions.assertTrue((allOrders.size()) != 0);
        assertThat(allOrders.get(0).getOwner().getName()).isEqualTo("Александр");
        assertThat(allOrders.get(1).getOwner().getName()).isEqualTo("Андрей");
    }
}