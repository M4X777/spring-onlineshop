package com.example.kr3.rest;

import com.example.kr3.model.COrder;
import com.example.kr3.model.CUser;
import com.example.kr3.repositories.IRepositoryOrders;
import com.example.kr3.repositories.IRepositoryUsers;
import com.example.kr3.services.CServiceCreateReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/report")
public class CControllerReports {

    @Autowired
    private IRepositoryOrders repositoryOrders;
    @Autowired
    private IRepositoryUsers repositoryUsers;

    @GetMapping(value = "/docx", produces = "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
    /**
     * Получение файла с отчетом
     */
    public void getFile() {
        List<COrder> orders = repositoryOrders.findAll();
        List<CUser> users = repositoryUsers.findAll();
        CServiceCreateReport.generateReport("C:\\Users\\maxim\\Desktop", orders, users);
    }
}
