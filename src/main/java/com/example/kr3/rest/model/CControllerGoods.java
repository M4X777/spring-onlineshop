package com.example.kr3.rest.model;

import com.example.kr3.model.CGood;
import com.example.kr3.repositories.IRepositoryGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/goods")
public class CControllerGoods {

    @Autowired
    private IRepositoryGoods repositoryGoods;

    @GetMapping(value = "")
    /**
     * Получение всех товаров из БД
     */
    public List<CGood> getAll() {
        return repositoryGoods.findAll();
    }

    /**
     * Получение одного товара по уникальному идентификатору
     */
    @GetMapping(params = "id")
    public Optional<CGood> getById(@RequestParam UUID id) {
        return repositoryGoods.findById(id);
    }

    /**
     *  Создание нового товара в БД
     */
    @PostMapping(value = "/save")
    public void saveGood(@RequestBody CGood good) { repositoryGoods.save(good); }

    /**
     * Удаление товара из БД
     */
    @DeleteMapping(value = "/delete")
    public void deleteGood(@RequestBody CGood good) {
        repositoryGoods.delete(good);
    }
}
