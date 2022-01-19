package com.example.kr3.repositories;

import com.example.kr3.model.CGood;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IRepositoryGoods extends JpaRepository<CGood, UUID>
{

}
