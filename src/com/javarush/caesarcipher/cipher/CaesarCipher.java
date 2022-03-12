package com.javarush.caesarcipher.cipher;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class CaesarCipher {
    private static final List<Character> ALPHABET
            = Arrays.asList(
            'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и',
            'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т',
            'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь',
            'э', 'ю', 'я', ' ', '!', '.', ',', '"', '?', ':',
            '\\', '\'');


    public static void encrypt(Path pathToDecryptedFile, Path pathToEncryptedFile, int cipherKey) {
        StringBuilder sb = new StringBuilder();

        try {
            String decryptedText = Files.readString(pathToDecryptedFile);
            for (char symbol : decryptedText.toCharArray()) {
                if (ALPHABET.contains(Character.toLowerCase(symbol))) {

                    int alphabetSymbolIndex = getAlphabetSymbolIndex(symbol);
                    int encryptSymbolIndex = (alphabetSymbolIndex + cipherKey) % alphabetSize();
                    char encryptSymbol = getAlphabetSymbol(encryptSymbolIndex);

                    sb.append(encryptSymbol);
                } else {
                    sb.append(symbol);
                }
            }
            Files.writeString(pathToEncryptedFile, sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void decrypt(Path pathToEncryptedFile, Path pathToDecryptedFile, int cipherKey) {
        StringBuilder sb = new StringBuilder();

        try {
            String encryptedText = Files.readString(pathToEncryptedFile);
            for (char symbol : encryptedText.toCharArray()) {
                if (ALPHABET.contains(Character.toLowerCase(symbol))) {

                    int alphabetSymbolIndex = getAlphabetSymbolIndex(symbol);
                    int decryptSymbolIndex = (alphabetSymbolIndex + (alphabetSize() - cipherKey)) % alphabetSize();
                    char decryptSymbol = getAlphabetSymbol(decryptSymbolIndex);

                    sb.append(decryptSymbol);
                } else {
                    sb.append(symbol);
                }
            }
            Files.writeString(pathToDecryptedFile, sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void bruteForce(Path pathToEncryptedFile, Path pathToDecryptedFile) {

    }


    private static int getAlphabetSymbolIndex(char symbol) {
        return ALPHABET.indexOf(Character.toLowerCase(symbol));
    }

    private static char getAlphabetSymbol(int encodeSymbolIndex) {
        return ALPHABET.get(encodeSymbolIndex);
    }

    public static int alphabetSize() {
        return ALPHABET.size();
    }
}
