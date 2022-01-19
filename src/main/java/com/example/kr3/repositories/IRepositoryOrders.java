package com.example.kr3.repositories;

import com.example.kr3.model.COrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IRepositoryOrders extends JpaRepository<COrder, UUID>
{

}
