package com.javarush.caesarcipher.cipher;

import java.io.BufferedReader;
import java.io.FileReader;
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

    public static final List<String> KEYWORDS
            = Arrays.asList(
            " не ", " я ", " и ", " на ", " с ",
            " в ", " к ", " под ", " из ", ", ",
            ": ", ". ", "! ", "? ");


    public static void encrypt(Path pathToDecryptedFile, Path pathToEncryptedFile, int cipherKey) throws IOException {
        StringBuilder builderResultText = new StringBuilder();

        String decryptedText = Files.readString(pathToDecryptedFile);

        for (char symbol : decryptedText.toCharArray()) {
            if (ALPHABET.contains(Character.toLowerCase(symbol))) {

                int alphabetSymbolIndex = getAlphabetSymbolIndex(symbol);
                int encryptSymbolIndex = (alphabetSymbolIndex + cipherKey) % alphabetSize();
                char encryptSymbol = getAlphabetSymbol(encryptSymbolIndex);

                builderResultText.append(encryptSymbol);
            } else {
                builderResultText.append(symbol);
            }
        }
        Files.writeString(pathToEncryptedFile, builderResultText.toString());
    }

    public static void decrypt(Path pathToEncryptedFile, Path pathToDecryptedFile, int cipherKey) throws IOException {
        StringBuilder builderResultText = new StringBuilder();

        String encryptedText = Files.readString(pathToEncryptedFile);

        for (char symbol : encryptedText.toCharArray()) {
            if (ALPHABET.contains(Character.toLowerCase(symbol))) {

                int alphabetSymbolIndex = getAlphabetSymbolIndex(symbol);
                int decryptSymbolIndex = (alphabetSymbolIndex + (alphabetSize() - cipherKey)) % alphabetSize();
                char decryptSymbol = getAlphabetSymbol(decryptSymbolIndex);

                builderResultText.append(decryptSymbol);
            } else {
                builderResultText.append(symbol);
            }
        }
        Files.writeString(pathToDecryptedFile, builderResultText.toString());
    }

    public static void bruteForce(Path pathToEncryptedFile, Path pathToDecryptedFile) throws IOException {
        StringBuilder builderResultText = new StringBuilder();
        StringBuilder builderEncryptedText = new StringBuilder();

        try (var readerEncryptedText = new BufferedReader(new FileReader(pathToEncryptedFile.toString()))) {
            while (readerEncryptedText.ready()) {
                builderEncryptedText.append(readerEncryptedText.readLine());
            }

            String encryptedText = builderEncryptedText.toString();
            int cipherKey = 1;

            while (cipherKey < alphabetSize()) {

                for (char symbol : encryptedText.toCharArray()) {
                    if (ALPHABET.contains(Character.toLowerCase(symbol))) {

                        int alphabetSymbolIndex = getAlphabetSymbolIndex(symbol);
                        int decryptSymbolIndex = (alphabetSymbolIndex + cipherKey) % alphabetSize();
                        char decryptSymbol = getAlphabetSymbol(decryptSymbolIndex);

                        builderResultText.append(decryptSymbol);
                    } else {
                        builderResultText.append(symbol);
                    }
                }

                String resultString = builderResultText.toString();
                int keywordHitsCount = 0;

                for (String keyword : KEYWORDS) {
                    if (resultString.contains(keyword)) keywordHitsCount++;
                }
                System.out.println(keywordHitsCount);

                if (keywordHitsCount > 3 && (resultString.endsWith(".") || resultString.endsWith("!") || resultString.endsWith("?"))
                ) {
                    System.out.println("Ключ шифрования: " + (alphabetSize() - cipherKey));
                    Files.writeString(pathToDecryptedFile, resultString);
                    return;
                } else {
                    builderResultText.setLength(0);
                    cipherKey++;
                }
            }
        }
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
