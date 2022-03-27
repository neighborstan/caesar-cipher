package com.javarush.caesarcipher.exceptions;

public class WriteFileException extends CaesarCipherIOException {
    private final String message;

    public WriteFileException(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Ошибка при записи файла " + message;
    }
}
