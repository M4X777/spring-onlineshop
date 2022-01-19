package com.example.kr3.rest.model;

import com.example.kr3.model.CUser;
import com.example.kr3.repositories.IRepositoryUsers;
import org.junit.jupiter.api.Assertions;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CControllerUsersTest {

    @Autowired
    private IRepositoryUsers repositoryUsers;

    @Test
    public void saveUserTest()
    {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        UUID id = UUID.randomUUID();
        CUser user = new CUser(id, "Maxim8567","Максим", "м", LocalDate.parse("1999-05-23", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        repositoryUsers.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
        ResponseEntity<Object> responseEntity = ResponseEntity.created(location).build();

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
        assertThat(responseEntity.getHeaders().getLocation().getPath()).isEqualTo("/" + id);
    }

    @Test
    public void getAllTest()
    {
        List<CUser> allUsers = repositoryUsers.getAllUsers();

        Assertions.assertTrue((allUsers.size()) != 0);
        assertThat(allUsers.get(0).getName()).isEqualTo("Александр");
        assertThat(allUsers.get(1).getName()).isEqualTo("Андрей");
    }

    @Test
    public void deleteUserTest(){
        List<CUser> users = repositoryUsers.findAll();
        // Берем последнего пользователя для удаления.
        CUser user = users.get(users.size() - 1);
        UUID id = user.getId();
        assertNotNull(user);
        repositoryUsers.delete(user);
        try {
            user = repositoryUsers.getById(id);
        } catch (final HttpClientErrorException exception){
            assertEquals(exception.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }
}