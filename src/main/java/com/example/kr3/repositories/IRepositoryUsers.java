package com.example.kr3.repositories;

import com.example.kr3.model.CUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public interface IRepositoryUsers extends JpaRepository<CUser, UUID>
{
    List<CUser> findBySex (String sex);

    @Query("SELECT u FROM CUser u")
    List<CUser> getAllUsers ();

    CUser findByLogin(String login);
}
