package com.javarush.caesarcipher.dialog;

import com.javarush.caesarcipher.cipher.CaesarCipher;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import static com.javarush.caesarcipher.dialog.ConsoleText.*;

public class UserDialog {
    public static final String ENCRYPT     = "E";
    public static final String DECRYPT     = "D";
    public static final String BRUTE_FORCE = "B";

    private static final String SMALL_TEXT  = "S";
    private static final String MEDIUM_TEXT = "M";
    private static final String LARGE_TEXT  = "XL";

    private static final CaesarCipher caesarCipher = CaesarCipher.getInstance();

    public static final int alphabetSize = caesarCipher.alphabetSize();

    private Path pathToDecryptedFile;
    private Path pathToEncryptedFile;
    private int cipherKey;
    private int bruteForceKey;



    public void start() {
        System.out.println(HELLO_TEXT);


        try (Scanner scanner = new Scanner(System.in)) {
            String action;
            do {
                System.out.println(CHOISE_OF_ACTION);
                System.out.println(ACTIONS_LIST_TEXT);

                action = scanner.nextLine();
            } while (!ENCRYPT.equals(action) && !DECRYPT.equals(action) && !BRUTE_FORCE.equals(action));

            switch (action) {
                case ENCRYPT -> {

                    System.out.println(ENCRYPTION_SELECTED);
                    System.out.println(ENTER_PATH_TO_DECRYPTED_FILE);
                    pathToDecryptedFile = getPathToFile(scanner);

                    System.out.println(ENTER_PATH_TO_ENCRYPTED_FILE);
                    pathToEncryptedFile = getPathToFile(scanner);

                    cipherKey = getValidCipherKey(scanner);

                    caesarCipher.encrypt(pathToDecryptedFile, pathToEncryptedFile, cipherKey);

                    System.out.println(RESULT_ACTION + pathToEncryptedFile.toAbsolutePath());
                }
                case DECRYPT -> {

                    System.out.println(DECRYPTION_SELECTED);
                    System.out.println(ENTER_PATH_TO_ENCRYPTED_FILE);
                    pathToEncryptedFile = getPathToFile(scanner);

                    System.out.println(ENTER_PATH_TO_DECRYPTED_FILE);
                    pathToDecryptedFile = getPathToFile(scanner);

                    cipherKey = getValidCipherKey(scanner);

                    caesarCipher.decrypt(pathToEncryptedFile, pathToDecryptedFile, cipherKey);

                    System.out.println(ConsoleText.RESULT_ACTION + pathToDecryptedFile.toAbsolutePath());
                }
                case BRUTE_FORCE -> {

                    System.out.println(BRUTE_FORCE_SELECTED);
                    System.out.println(ENTER_PATH_TO_ENCRYPTED_FILE);
                    pathToEncryptedFile = getPathToFile(scanner);

                    System.out.println(ENTER_PATH_TO_DECRYPTED_FILE);
                    pathToDecryptedFile = getPathToFile(scanner);

                    bruteForceKey = getBruteForceKey(scanner);

                    caesarCipher.bruteForce(pathToEncryptedFile, pathToDecryptedFile, bruteForceKey);

                    System.out.println(ConsoleText.RESULT_ACTION + pathToDecryptedFile.toAbsolutePath());
                }
            }
        }
    }

    private int getValidCipherKey(Scanner scanner) {
        int key;
        String keyString;

        while (true) {
            System.out.println(ENTER_CIPHER_KEY);

            keyString = scanner.nextLine();
            if (isInteger(keyString)) {
                key = Integer.parseInt(keyString);

                if (key > 0 && key < alphabetSize) {
                    return key;
                }
            }
        }
    }

    private int getBruteForceKey(Scanner scanner) {
        int bruteForceKey;
        String stringKey;

        while (true) {
            System.out.println(CHOISE_OF_BRUTE_FORCE_KEY);
            System.out.println(BRUTE_FORCE_KEYS_LIST);

            stringKey = scanner.nextLine();
            switch (stringKey) {
                case SMALL_TEXT -> bruteForceKey  = 2;
                case MEDIUM_TEXT -> bruteForceKey = 5;
                case LARGE_TEXT -> bruteForceKey  = 8;
                default -> {
                    continue;
                }
            }
            return bruteForceKey;
        }
    }

    private Path getPathToFile(Scanner scanner) {
        Path pathToFile = Path.of(scanner.nextLine());

        while (!Files.isRegularFile(pathToFile)) {
            System.out.println(FILE_NOT_EXIST_WARNING);
            pathToFile = Path.of(scanner.nextLine());
        }
        return pathToFile;
    }

    private boolean isInteger(String keyString) {
        try {
            Integer.parseInt(keyString);
        } catch(NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }
}
