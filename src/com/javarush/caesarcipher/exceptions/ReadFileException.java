package com.javarush.caesarcipher.exceptions;

public class ReadFileException extends CaesarCipherIOException {
    private final String message;

    public ReadFileException(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Ошибка при чтении файла " + message;
    }
}
