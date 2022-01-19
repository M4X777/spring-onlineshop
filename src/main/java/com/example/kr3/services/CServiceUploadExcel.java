package com.example.kr3.services;

import com.example.kr3.model.CGood;
import com.example.kr3.model.COrder;
import com.example.kr3.model.CUser;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CServiceUploadExcel {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    /**
     * Сравнение типа загружаемого файла (должен быть Excel)
     * @param file - файл для загрузки в БД
     **/
    public static boolean hasExcelFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    /**************************************************************************************************
     * Считывание пользователей из Excel-файла
     * @param inputStream - входной поток данных (из Excel-файла)
     **************************************************************************************************/
    public static List<CUser> excelUsersToDB(InputStream inputStream) throws IOException {
        ArrayList<CUser> users = new ArrayList<>();
        try (XSSFWorkbook wb = new XSSFWorkbook(inputStream))
        {
            Sheet sheet = wb.getSheet("Пользователи");
            int rows = sheet.getLastRowNum();
            Row row;
            Cell cell;
            String temp, login, name, sex;
            UUID id;
            LocalDate dateOfBirth;

            for (int i=1; i<=rows; i++) {
                row = sheet.getRow(i);
                if (row==null)
                    continue;

                cell = row.getCell(0);
                if(!checkCellisNull(cell, "Пользователи"))
                    break;
                temp = cell.getStringCellValue();
                id = UUID.fromString(temp);

                cell = row.getCell(1);
                login = cell.getStringCellValue();

                cell = row.getCell(2);
                name = cell.getStringCellValue();

                cell = row.getCell(3);
                sex = cell.getStringCellValue();

                cell = row.getCell(4);
                // Преобразование даты из строки в специальный объект LocalDate.
                dateOfBirth = cell.getDateCellValue().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();
                users.add(new CUser(id, login, name, sex, dateOfBirth));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }


    /***************************************************************************************************
     * Считывание покупок из Excel-файла
     * @param inputStream - входной поток данных (из Excel-файла)
     * @param users - список пользователей
     * @param goods - список товаров
     **************************************************************************************************/
    public static List<COrder> excelOrdersToDB(InputStream inputStream, List<CUser> users, List<CGood> goods) {
        ArrayList<COrder> orders = new ArrayList<>();
        try (XSSFWorkbook wb = new XSSFWorkbook(inputStream))
        {
            Sheet sheet = wb.getSheet("Покупки");
            int rows = sheet.getLastRowNum();
            Row row;
            UUID id, userId;
            String temp;
            Cell cell;
            LocalDate dateOfPurchase;
            CUser user = null;
            CGood good = null;

            for (int i=1; i<=rows; i++) {
                row = sheet.getRow(i);
                if (row==null)
                    continue;

                cell = row.getCell(0);
                if(!checkCellisNull(cell, "Покупки"))
                    break;
                temp = cell.getStringCellValue();
                userId = UUID.fromString(temp);

                cell = row.getCell(1);
                temp = cell.getStringCellValue();
                id = UUID.fromString(temp);

                cell = row.getCell(2);
                // Преобразование даты из строки в специальный объект LocalDate.
                dateOfPurchase = cell.getDateCellValue().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();

                // Ищем владельца заказа и "отправляем" его в конструктор COrder. Создаём заказ.
                for (CUser cUser : users) {
                    if (cUser.getId().equals(userId)) {
                        user = cUser;
                        break;
                    }
                }
                // Ищем товар и "отправляем" его в конструктор COrder. Создаём заказ.
                for (CGood cGood : goods) {
                    if (cGood.getId().equals(id)) {
                        good = cGood;
                        break;
                    }
                }

                orders.add(new COrder(id, user, good, dateOfPurchase));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }

    /**************************************************************************************************
     * Считывание товаров из Excel-файла
     * @param inputStream - входной поток данных (из Excel-файла)
     **************************************************************************************************/
    public static List<CGood> excelGoodsToDB(InputStream inputStream) {
        ArrayList<CGood> goods = new ArrayList<>();
        try (XSSFWorkbook wb = new XSSFWorkbook(inputStream))
        {
            Sheet sheet = wb.getSheet("Товары");
            int rows = sheet.getLastRowNum();
            Row row;
            Cell cell;
            String temp, category, name;
            UUID id;
            double costOfGood;

            for (int i=1; i<=rows; i++) {
                row = sheet.getRow(i);
                if (row==null)
                    continue;

                cell = row.getCell(0);
                if(!checkCellisNull(cell, "Товары"))
                    break;
                temp = cell.getStringCellValue();
                id = UUID.fromString(temp);

                cell = row.getCell(1);
                name = cell.getStringCellValue();

                cell = row.getCell(2);
                costOfGood = cell.getNumericCellValue();

                cell = row.getCell(3);
                category = cell.getStringCellValue();

                goods.add(new CGood(id, name, costOfGood, category));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return goods;
    }
    /****************************************************************************************************
     * Проверка на null ячейки в строке
     * @param cell - проверяемая ячейка
     * @param sheetName - название таблицы (листа)
     ***************************************************************************************************/
    private static boolean checkCellisNull(Cell cell, String sheetName) {
        if (cell == null) {
            System.out.println("В таблице \"" + sheetName + "\" пустая ячейка! Проверьте данные!");
            return false;
        }
        return true;
    }
}
