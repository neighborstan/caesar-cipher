package com.javarush.caesarcipher.cipher;

import com.javarush.caesarcipher.exceptions.ReadFileException;
import com.javarush.caesarcipher.exceptions.WriteFileException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileProcessing {

    public static String readString(Path pathToFile) throws ReadFileException {
        try {
            return Files.readString(pathToFile);
        } catch (IOException e) {
            throw new ReadFileException(pathToFile + "\n" + e);
        }
    }

    public static void writeString(Path pathToFile, String textString) throws WriteFileException {
        try {
            Files.writeString(pathToFile, textString);
        } catch (IOException e) {
            throw new WriteFileException(pathToFile + "\n" + e);
        }
    }
}
