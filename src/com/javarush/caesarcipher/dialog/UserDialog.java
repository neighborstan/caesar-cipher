package com.javarush.caesarcipher.dialog;

import com.javarush.caesarcipher.cipher.CaesarCipher;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import static com.javarush.caesarcipher.dialog.ConsoleText.*;

public class UserDialog {
    private static final String BRUTE_FORCE_KEYS_LIST = "%s - небольшой текст\n%s - средний текст\n%s - большой текст".formatted(SMALL_TEXT_KEY, MEDIUM_TEXT_KEY, LARGE_TEXT_KEY);
    private static final String ACTIONS_LIST_TEXT = "%s - зашифровать файл\n%s - расшифровать файл по ключу\n%s - расшифровать файл подбором (brute force)".formatted(ENCRYPT_ACTION_KEY, DECRYPT_ACTION_KEY, BRUTE_FORCE_ACTION_KEY);

    private static final CaesarCipher CAESAR_CIPHER = CaesarCipher.getInstance();

    private static final int ALPHABET_SIZE = CAESAR_CIPHER.alphabetSize();

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
            } while (!ENCRYPT_ACTION_KEY.equals(action) && !DECRYPT_ACTION_KEY.equals(action) && !BRUTE_FORCE_ACTION_KEY.equals(action));

            switch (action) {
                case ENCRYPT_ACTION_KEY -> {

                    System.out.println(ENCRYPTION_SELECTED);
                    System.out.println(ENTER_PATH_TO_DECRYPTED_FILE);
                    pathToDecryptedFile = getPathToFile(scanner);

                    System.out.println(ENTER_PATH_TO_ENCRYPTED_FILE);
                    pathToEncryptedFile = getPathToFile(scanner);

                    cipherKey = getValidCipherKey(scanner);

                    CAESAR_CIPHER.encrypt(pathToDecryptedFile, pathToEncryptedFile, cipherKey);

                    System.out.println(RESULT_ACTION + pathToEncryptedFile.toAbsolutePath());
                }
                case DECRYPT_ACTION_KEY -> {

                    System.out.println(DECRYPTION_SELECTED);
                    System.out.println(ENTER_PATH_TO_ENCRYPTED_FILE);
                    pathToEncryptedFile = getPathToFile(scanner);

                    System.out.println(ENTER_PATH_TO_DECRYPTED_FILE);
                    pathToDecryptedFile = getPathToFile(scanner);

                    cipherKey = getValidCipherKey(scanner);

                    CAESAR_CIPHER.decrypt(pathToEncryptedFile, pathToDecryptedFile, cipherKey);

                    System.out.println(ConsoleText.RESULT_ACTION + pathToDecryptedFile.toAbsolutePath());
                }
                case BRUTE_FORCE_ACTION_KEY -> {

                    System.out.println(BRUTE_FORCE_SELECTED);
                    System.out.println(ENTER_PATH_TO_ENCRYPTED_FILE);
                    pathToEncryptedFile = getPathToFile(scanner);

                    System.out.println(ENTER_PATH_TO_DECRYPTED_FILE);
                    pathToDecryptedFile = getPathToFile(scanner);

                    bruteForceKey = getBruteForceKey(scanner);

                    CAESAR_CIPHER.bruteForce(pathToEncryptedFile, pathToDecryptedFile, bruteForceKey);

                    System.out.println(ConsoleText.RESULT_ACTION + pathToDecryptedFile.toAbsolutePath());
                }
            }
        }
    }

    private int getValidCipherKey(Scanner scanner) {
        int key;
        String keyString;

        while (true) {
            System.out.println(ENTER_CIPHER_KEY + (ALPHABET_SIZE - 1));

            keyString = scanner.nextLine();
            if (isInteger(keyString)) {
                key = Integer.parseInt(keyString);

                if (key > 0 && key < ALPHABET_SIZE) {
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
                case SMALL_TEXT_KEY -> bruteForceKey = 2;
                case MEDIUM_TEXT_KEY -> bruteForceKey = 5;
                case LARGE_TEXT_KEY -> bruteForceKey = 8;
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
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }
}
