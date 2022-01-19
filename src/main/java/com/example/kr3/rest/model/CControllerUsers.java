package com.example.kr3.rest.model;

import com.example.kr3.model.CUser;
import com.example.kr3.repositories.IRepositoryUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping(value = "/users")
public class CControllerUsers {

    @Autowired
    private static IRepositoryUsers repositoryUsers;

    @GetMapping(value = "")
    public List<CUser> getAll() {
        return repositoryUsers.findAll();
    }

    @GetMapping(params = "id")
    public Optional<CUser> getById(@RequestParam UUID id) {
        return repositoryUsers.findById(id);
    }

    @GetMapping(params = "sex")
    public List<CUser> getBySex(@RequestParam String sex) {
        return repositoryUsers.findBySex(sex);
    }


    @GetMapping(value = "/all")
    public List<CUser> getAllUsers() { return repositoryUsers.getAllUsers(); }

    @PostMapping(value = "/save")
    public void saveUser(@RequestBody CUser user) { repositoryUsers.save(user); }

    @DeleteMapping(value = "/delete")
    public void deleteUser(@RequestBody CUser user) {
        repositoryUsers.delete(user);
    }

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("title", "Пользователи");
        return "home";
    }
}
