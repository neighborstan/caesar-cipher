package com.javarush.caesarcipher.cipher;

import com.javarush.caesarcipher.exceptions.CaesarCipherIOException;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class CaesarCipher {
    private static CaesarCipher instance;

    private static final List<Character> ALPHABET = Arrays.asList(
            'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и',
            'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т',
            'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь',
            'э', 'ю', 'я', ' ', '!', '.', ',', '"', '?', ':',
            '\\', '\''
    );

    private static final List<String> KEYWORDS = Arrays.asList(
            " не ", " я ", " и ", " на ", " с ",
            " в ", " к ", " под ", " из ", ", ",
            ": ", ". ", "! ", "? ", " - ", " у "
    );

    private CaesarCipher() {}

    public static CaesarCipher getInstance() {
        if (instance == null) {
            instance = new CaesarCipher();
        }
        return instance;
    }

    public void encrypt(Path inputFilePath, Path outputFilePath, int cipherKey) {
        StringBuilder builderResultText = new StringBuilder();

        String decryptedText = FileProcessing.readString(inputFilePath);

        for (char symbol : decryptedText.toCharArray()) {
            if (ALPHABET.contains(Character.toLowerCase(symbol))) {

                int alphabetSymbolIndex = getAlphabetSymbolIndex(symbol);
                int encryptSymbolIndex = (alphabetSymbolIndex + cipherKey) % alphabetSize();
                symbol = getAlphabetSymbol(encryptSymbolIndex);
            }
            builderResultText.append(symbol);
        }
        FileProcessing.writeString(outputFilePath, builderResultText.toString());
    }

    public void decrypt(Path inputFilePath, Path outputFilePath, int cipherKey) {
        StringBuilder builderResultText = new StringBuilder();

        String encryptedText = FileProcessing.readString(inputFilePath);

        for (char symbol : encryptedText.toCharArray()) {
            if (ALPHABET.contains(Character.toLowerCase(symbol))) {

                int alphabetSymbolIndex = getAlphabetSymbolIndex(symbol);
                int decryptSymbolIndex = (alphabetSymbolIndex + (alphabetSize() - cipherKey)) % alphabetSize();
                symbol = getAlphabetSymbol(decryptSymbolIndex);
            }
            builderResultText.append(symbol);
        }
        FileProcessing.writeString(outputFilePath, builderResultText.toString());
    }

    public void bruteForce(Path inputFilePath, Path outputFilePath, int textSizeKey) {
        StringBuilder builderResultText = new StringBuilder();

        String encryptedText = FileProcessing.readString(inputFilePath);
        int cipherKey = 1;

        while (cipherKey < alphabetSize()) {

            for (char symbol : encryptedText.toCharArray()) {
                if (ALPHABET.contains(Character.toLowerCase(symbol))) {

                    int alphabetSymbolIndex = getAlphabetSymbolIndex(symbol);
                    int decryptSymbolIndex = (alphabetSymbolIndex + cipherKey) % alphabetSize();
                    symbol = getAlphabetSymbol(decryptSymbolIndex);
                }
                builderResultText.append(symbol);
            }
            String result = builderResultText.toString();
            int keywordHitsCount = getKeywordHitsCount(result);

            if (isDecryptedText(keywordHitsCount, textSizeKey, result)) {

                System.out.println("Ключ шифрования: " + (alphabetSize() - cipherKey));
                FileProcessing.writeString(outputFilePath, result);
                return;
            } else {
                builderResultText.setLength(0);
                cipherKey++;
            }
        }
    }

    public int alphabetSize() {
        return ALPHABET.size();
    }

    private boolean isDecryptedText(int keywordHitsCount, int textSizeKey, String text) {
        return (keywordHitsCount >= textSizeKey) && isLastSymbolValid(text);
    }

    private boolean isLastSymbolValid(String text) {
        return text.endsWith(".") || text.endsWith("!") || text.endsWith("?");
    }

    private int getKeywordHitsCount(String text) {
        int keywordHitsCount = 0;

        for (String keyword : KEYWORDS) {
            if (text.contains(keyword)) {
                keywordHitsCount++;
            }
        }
        return keywordHitsCount;
    }

    private int getAlphabetSymbolIndex(char symbol) {
        return ALPHABET.indexOf(Character.toLowerCase(symbol));
    }

    private char getAlphabetSymbol(int encodeSymbolIndex) {
        return ALPHABET.get(encodeSymbolIndex);
    }
}
