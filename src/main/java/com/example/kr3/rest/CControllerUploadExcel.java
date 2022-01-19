package com.example.kr3.rest;

import com.example.kr3.model.CGood;
import com.example.kr3.model.COrder;
import com.example.kr3.model.CUser;
import com.example.kr3.repositories.IRepositoryGoods;
import com.example.kr3.repositories.IRepositoryOrders;
import com.example.kr3.repositories.IRepositoryUsers;
import com.example.kr3.services.CServiceResponseMessage;
import com.example.kr3.services.CServiceUploadExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/upload_data")
public class CControllerUploadExcel {

    @Autowired
    private IRepositoryOrders repositoryOrders;
    @Autowired
    private IRepositoryGoods repositoryGoods;
    @Autowired
    private IRepositoryUsers repositoryUsers;

    @PostMapping
    /**
     * Загрузка файла Excel в БД
     */
    public ResponseEntity<CServiceResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        if (CServiceUploadExcel.hasExcelFormat(file)) {
            try {
                try {
                    // Получаем пользователей, товары и заказы из Excel и собираем их в список соответствующих объектов Java.
                    List<CUser> users = CServiceUploadExcel.excelUsersToDB(file.getInputStream());
                    List<CGood> goods = CServiceUploadExcel.excelGoodsToDB(file.getInputStream());
                    List<COrder> orders = CServiceUploadExcel.excelOrdersToDB(file.getInputStream(), users, goods);
                    // Сохраняем эти объекты в БД.
                    repositoryUsers.saveAll(users);
                    repositoryGoods.saveAll(goods);
                    repositoryOrders.saveAll(orders);
                } catch (IOException e) {
                    throw new RuntimeException("Не удаётся сохранить данные Excel: " + e.getMessage());
                }

                message = "Успешно загружен файл: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new CServiceResponseMessage(message));
            } catch (Exception e) {
                message = "Не удалось загрузить файл: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new CServiceResponseMessage(message));
            }
        }

        message = "Пожалуйста, загрузите файл Excel!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CServiceResponseMessage(message));
    }
}
