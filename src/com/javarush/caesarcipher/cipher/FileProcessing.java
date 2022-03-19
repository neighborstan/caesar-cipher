package com.javarush.caesarcipher.cipher;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileProcessing {

    public static String readString(Path pathToFile){
        String resultText = "";
        try {
            resultText = Files.readString(pathToFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultText;
    }

    public static void writeString(Path pathToFile, String textString){
        try {
            Files.writeString(pathToFile, textString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
