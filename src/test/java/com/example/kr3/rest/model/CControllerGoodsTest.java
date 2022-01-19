package com.example.kr3.rest.model;

import com.example.kr3.model.CGood;
import com.example.kr3.repositories.IRepositoryGoods;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CControllerGoodsTest {

    @Autowired
    private IRepositoryGoods repositoryGoods;

    @Test
    void saveGoodTest()
    {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        UUID id = UUID.randomUUID();
        CGood good = new CGood(id, "Компьютер", 100.0, "Техника");
        repositoryGoods.save(good);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(good.getId())
                .toUri();
        ResponseEntity<Object> responseEntity = ResponseEntity.created(location).build();

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
        assertThat(responseEntity.getHeaders().getLocation().getPath()).isEqualTo("/" + id);
    }

    @Test
    void getAllTest()
    {
        List<CGood> allGoods = repositoryGoods.findAll();

        assertTrue((allGoods.size()) != 0);
        assertThat(allGoods.get(0).getName()).isEqualTo("Велосипед");
        assertThat(allGoods.get(1).getName()).isEqualTo("Турник");
    }

    @Test
    public void deleteGoodTest(){
        List<CGood> goods = repositoryGoods.findAll();
        // Берем последнего пользователя для удаления.
        CGood good = goods.get(goods.size() - 1);
        UUID id = good.getId();
        assertNotNull(good);
        repositoryGoods.delete(good);
        try {
            good = repositoryGoods.getById(id);
        } catch (final HttpClientErrorException exception){
            assertEquals(exception.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }
}