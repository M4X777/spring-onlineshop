package com.example.kr3.services;

public class CServiceResponseMessage {
    private String message;

    /**
     * Вывод сообщения в консоль
     * @param message - текст сообщения
     */
    public CServiceResponseMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}