package com.example.kr3.services;

import com.example.kr3.model.COrder;
import com.example.kr3.model.CUser;
import org.apache.poi.xwpf.usermodel.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class CServiceCreateReport {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    /****************************************************************************************************
     * Создание Word-файла с карточками пользователей
     * @param usersWithMinPurchase - коллекция UUID пользователей с минимальным количеством покупок
     * @param minNumberGoods - минимальное количество покупок
     * @param path - путь сохранения отчета
     * @param users - список пользователей
     ***************************************************************************************************/
    private static void createWord(Set<UUID> usersWithMinPurchase, Integer minNumberGoods, String path, List<CUser> users)
    {
        try (XWPFDocument doc = new XWPFDocument()) {

            for (UUID id : usersWithMinPurchase) {
                for (CUser user : users) {
                    if (user.getId().equals(id)) {
                        XWPFParagraph para = doc.createParagraph();
                        para.setAlignment(ParagraphAlignment.CENTER);
                        XWPFRun r1 = para.createRun();
                        r1.setBold(true);
                        r1.setText(user.getName());
                        r1.setFontFamily("Times New Roman");
                        r1.setUnderline(UnderlinePatterns.SINGLE);
                        r1.setFontSize(16);
                        r1.setText("\n");

                        // Создание таблицы
                        XWPFTable table = doc.createTable();

                        // Создание строк таблицы
                        XWPFTableRow rowName = table.getRow(0);
                        rowName.getCell(0).setText("Логин:");
                        rowName.addNewTableCell().setText(""+user.getLogin());

                        XWPFTableRow rowSex = table.createRow();
                        rowSex.getCell(0).setText("Пол:");
                        rowSex.getCell(1).setText(""+user.getSex());

                        XWPFTableRow rowAge = table.createRow();
                        rowAge.getCell(0).setText("Возраст:");
                        rowAge.getCell(1).setText(""+user.getAge());

                        XWPFTableRow rowDateofBirth = table.createRow();
                        rowDateofBirth.getCell(0).setText("Дата рождения:");
                        rowDateofBirth.getCell(1).setText(""+user.getDateOfBirth().format(formatter));

                        XWPFTableRow rowId = table.createRow();
                        rowId.getCell(0).setText("Идентификатор:");
                        rowId.getCell(1).setText(""+user.getId());

                        XWPFTableRow rowNumGoods = table.createRow();
                        rowNumGoods.getCell(0).setText("Количество покупок:");
                        rowNumGoods.getCell(1).setText(""+minNumberGoods);

                        XWPFParagraph tab_para = doc.createParagraph();
                    }
                }
            }

            try (FileOutputStream out = new FileOutputStream(new File(path + "\\user_report.docx"))) {
                doc.write(out);
            } catch (FileNotFoundException e) {
                System.out.println("Файл не найден: user_report.docx");
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /****************************************************************************************************
     * Поиск пользователей с минимальным количеством товаров
     * @param purchasedGoods - коллекция UUID пользователей (key) с количеством покупок (value)
     * @param minNumberGoods - минимальное количество покупок
     ***************************************************************************************************/
    private static Set<UUID> getKeysByValue(TreeMap<UUID, Integer> purchasedGoods, Integer minNumberGoods) {
        return purchasedGoods.entrySet()
                .stream()
                .filter(entry -> Objects.equals(entry.getValue(), minNumberGoods))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    /**************************************************************************************************
     * Подсчёт количества покупок у каждого пользователя
     **************************************************************************************************
     * @param orders - список заказов из БД*/
    private static TreeMap<UUID, Integer> getPurchasedGoods(List<COrder> orders)
    {
        TreeMap<UUID, Integer> purchasedGoods = new TreeMap<>();
        for (COrder order : orders)
        {
            UUID userId = order.owner.getId();
            if (purchasedGoods.containsKey(userId))
            {
                purchasedGoods.put(userId, purchasedGoods.get(userId)+1);
            }
            else
            {
                purchasedGoods.put(userId, 1);
            }
        }
        return purchasedGoods;
    }

    public static void generateReport(String path, List<COrder> orders, List<CUser> users) {
        // Получаем пользователей с количеством покупок.
        TreeMap<UUID, Integer> purchasedGoods = getPurchasedGoods(orders);
        if (purchasedGoods.size() == 0) {
            System.out.println("Не совершено ни одной покупки!");
            return;
        }
        // Получаем число минимального количества товаров.
        Integer minNumberGoods = Collections.min(purchasedGoods.entrySet(), Map.Entry.comparingByValue()).getValue();
        if (minNumberGoods == 0) {
            System.out.println("Количество покупок равно 0!");
            return;
        }
        // Получаем id пользователей с минимальным количеством товаров.
        Set<UUID> usersWithMinPurchase = getKeysByValue(purchasedGoods, minNumberGoods);
        // Выводим карточки пользователей в Word.
        createWord(usersWithMinPurchase, minNumberGoods, path, users);
    }
}
