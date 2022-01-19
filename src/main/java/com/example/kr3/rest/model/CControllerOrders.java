package com.example.kr3.rest.model;

import com.example.kr3.model.COrder;
import com.example.kr3.repositories.IRepositoryOrders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/orders")
public class CControllerOrders {

    @Autowired
    private IRepositoryOrders repositoryOrders;

    @GetMapping(value = "")
    /**
     * Получение всех заказов из БД
     */
    public List<COrder> getAll() {
        return repositoryOrders.findAll();
    }

    /**
     * Получение одного заказа по уникальному идентификатору
     */
    @GetMapping(params = "id")
    public Optional<COrder> getById(@RequestParam UUID id) {
        return repositoryOrders.findById(id);
    }

    @DeleteMapping(value = "/delete")
    /**
     * Удаление заказа из БД
     */
    public void deleteOrder(@RequestBody COrder order) {
        repositoryOrders.delete(order);
    }
}
