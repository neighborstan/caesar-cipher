package com.javarush.caesarcipher.cipher;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileProcessing {
    private static final String READ_FILE_ERROR = "Ошибка при чтении файла!";
    private static final String WRITE_FILE_ERROR = "Ошибка при записи файла!";

    public static String readString(Path pathToFile){
        String resultText;
        try {
            resultText = Files.readString(pathToFile);

        } catch (IOException e) {
            System.err.println(READ_FILE_ERROR);
            resultText = e.getMessage();
            e.printStackTrace();
        }
        return resultText;
    }

    public static void writeString(Path pathToFile, String textString){
        try {
            Files.writeString(pathToFile, textString);
        } catch (IOException e) {
            System.err.println(WRITE_FILE_ERROR);
            e.printStackTrace();
        }
    }
}
