package com.javarush.caesarcipher.cipher;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class CaesarCipher {
    private static CaesarCipher instance;

    private static final List<Character> ALPHABET
            = Arrays.asList(
            'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и',
            'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т',
            'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь',
            'э', 'ю', 'я', ' ', '!', '.', ',', '"', '?', ':',
            '\\', '\'');

    private static final List<String> KEYWORDS
            = Arrays.asList(
            " не ", " я ", " и ", " на ", " с ",
            " в ", " к ", " под ", " из ", ", ",
            ": ", ". ", "! ", "? ", " - ", " у ");

    private CaesarCipher() {}

    public static CaesarCipher getInstance(){
        if(instance == null){
            instance = new CaesarCipher();
        }
        return instance;
    }

    public void encrypt(Path pathToDecryptedFile, Path pathToEncryptedFile, int cipherKey) {
        StringBuilder builderResultText = new StringBuilder();

        String decryptedText = FileProcessing.readString(pathToDecryptedFile);

        for (char symbol : decryptedText.toCharArray()) {
            if (ALPHABET.contains(Character.toLowerCase(symbol))) {

                int alphabetSymbolIndex = getAlphabetSymbolIndex(symbol);
                int encryptSymbolIndex = (alphabetSymbolIndex + cipherKey) % alphabetSize();
                symbol = getAlphabetSymbol(encryptSymbolIndex);
            }
                builderResultText.append(symbol);
        }
        FileProcessing.writeString(pathToEncryptedFile, builderResultText.toString());
    }

    public void decrypt(Path pathToEncryptedFile, Path pathToDecryptedFile, int cipherKey) {
        StringBuilder builderResultText = new StringBuilder();

        String encryptedText = FileProcessing.readString(pathToEncryptedFile);

        for (char symbol : encryptedText.toCharArray()) {
            if (ALPHABET.contains(Character.toLowerCase(symbol))) {

                int alphabetSymbolIndex = getAlphabetSymbolIndex(symbol);
                int decryptSymbolIndex = (alphabetSymbolIndex + (alphabetSize() - cipherKey)) % alphabetSize();
                symbol = getAlphabetSymbol(decryptSymbolIndex);
            }
                builderResultText.append(symbol);
        }
        FileProcessing.writeString(pathToDecryptedFile, builderResultText.toString());
    }

    public void bruteForce(Path pathToEncryptedFile, Path pathToDecryptedFile, int bruteForceKey) {
        StringBuilder builderResultText = new StringBuilder();

            String encryptedText = FileProcessing.readString(pathToEncryptedFile);
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

                String resultString = builderResultText.toString();
                int keywordHitsCount = 0;

                for (String keyword : KEYWORDS) {
                    if (resultString.contains(keyword)) keywordHitsCount++;
                }

                if (keywordHitsCount >= bruteForceKey && (resultString.endsWith(".") || resultString.endsWith("!") || resultString.endsWith("?"))
                ) {
                    System.out.println("Ключ шифрования: " + (alphabetSize() - cipherKey));
                    FileProcessing.writeString(pathToDecryptedFile, resultString);
                    return;
                } else {
                    builderResultText.setLength(0);
                    cipherKey++;
                }
            }

    }

    private int getAlphabetSymbolIndex(char symbol) {
        return ALPHABET.indexOf(Character.toLowerCase(symbol));
    }

    private char getAlphabetSymbol(int encodeSymbolIndex) {
        return ALPHABET.get(encodeSymbolIndex);
    }

    public int alphabetSize() {
        return ALPHABET.size();
    }
}
