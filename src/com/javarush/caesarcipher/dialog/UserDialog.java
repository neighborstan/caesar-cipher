package com.javarush.caesarcipher.dialog;

import com.javarush.caesarcipher.cipher.CaesarCipher;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import static com.javarush.caesarcipher.dialog.ConsoleText.*;

public class UserDialog {
    private final String BRUTE_FORCE_KEYS_LIST = "%s - небольшой текст\n%s - средний текст\n%s - большой текст".formatted(TextSizeKey.SMALL.getStringKey(), TextSizeKey.MEDIUM.getStringKey(), TextSizeKey.LARGE.getStringKey());
    private final String ACTIONS_LIST_TEXT = "%s - зашифровать файл\n%s - расшифровать файл по ключу\n%s - расшифровать файл подбором (brute force)".formatted(ENCRYPT_ACTION_KEY, DECRYPT_ACTION_KEY, BRUTE_FORCE_ACTION_KEY);

    private final CaesarCipher CAESAR_CIPHER = CaesarCipher.getInstance();

    private final int ALPHABET_SIZE = CAESAR_CIPHER.alphabetSize();

    private Path pathToDecryptedFile;
    private Path pathToEncryptedFile;
    private int cipherKey;
    private int bruteForceTextKey;


    public void start() {
        System.out.println(HELLO_TEXT);

        try (Scanner scanner = new Scanner(System.in)) {
            String action;
            do {
                System.out.println(CHOISE_OF_ACTION_MESSAGE);
                System.out.println(ACTIONS_LIST_TEXT);

                action = scanner.nextLine();
            } while (!isActionValid(action));

            if (ENCRYPT_ACTION_KEY.equals(action)) {
                System.out.println(ENCRYPTION_SELECTED_MESSAGE);
                System.out.println(ENTER_PATH_TO_DECRYPTED_FILE_MESSAGE);
                pathToDecryptedFile = getPathToFile(scanner);

                System.out.println(ENTER_PATH_TO_ENCRYPTED_FILE_MESSAGE);
                pathToEncryptedFile = getPathToFile(scanner);

                cipherKey = getValidCipherKey(scanner);

                CAESAR_CIPHER.encrypt(pathToDecryptedFile, pathToEncryptedFile, cipherKey);

                System.out.println(RESULT_ACTION_MESSAGE + pathToEncryptedFile.toAbsolutePath());

            } else if (DECRYPT_ACTION_KEY.equals(action)) {
                System.out.println(DECRYPTION_SELECTED_MESSAGE);
                System.out.println(ENTER_PATH_TO_ENCRYPTED_FILE_MESSAGE);
                pathToEncryptedFile = getPathToFile(scanner);

                System.out.println(ENTER_PATH_TO_DECRYPTED_FILE_MESSAGE);
                pathToDecryptedFile = getPathToFile(scanner);

                cipherKey = getValidCipherKey(scanner);

                CAESAR_CIPHER.decrypt(pathToEncryptedFile, pathToDecryptedFile, cipherKey);

                System.out.println(RESULT_ACTION_MESSAGE + pathToDecryptedFile.toAbsolutePath());

            } else if (BRUTE_FORCE_ACTION_KEY.equals(action)) {
                System.out.println(BRUTE_FORCE_SELECTED_MESSAGE);
                System.out.println(ENTER_PATH_TO_ENCRYPTED_FILE_MESSAGE);
                pathToEncryptedFile = getPathToFile(scanner);

                System.out.println(ENTER_PATH_TO_DECRYPTED_FILE_MESSAGE);
                pathToDecryptedFile = getPathToFile(scanner);

                bruteForceTextKey = getBruteForceTextKey(scanner);

                CAESAR_CIPHER.bruteForce(pathToEncryptedFile, pathToDecryptedFile, bruteForceTextKey);

                System.out.println(RESULT_ACTION_MESSAGE + pathToDecryptedFile.toAbsolutePath());
            }
        }
    }

    private boolean isActionValid(String action) {
        return ENCRYPT_ACTION_KEY.equals(action) || DECRYPT_ACTION_KEY.equals(action) || BRUTE_FORCE_ACTION_KEY.equals(action);
    }

    private int getValidCipherKey(Scanner scanner) {
        while (true) {
            System.out.println(ENTER_CIPHER_KEY_MESSAGE + (ALPHABET_SIZE - 1));

            String keyString = scanner.nextLine();
            if (isInteger(keyString)) {
                int key = Integer.parseInt(keyString);

                if (isValidCipherKeyRange(key)) {
                    return key;
                }
            }
        }
    }

    private int getBruteForceTextKey(Scanner scanner) {
        String stringKey;
        System.out.println(CHOISE_OF_BRUTE_FORCE_KEY_MESSAGE);
        System.out.println(BRUTE_FORCE_KEYS_LIST);

        while (!TextSizeKey.getKeys().containsKey(stringKey = scanner.nextLine())) {
            System.out.println(CHOISE_OF_BRUTE_FORCE_KEY_MESSAGE);
            System.out.println(BRUTE_FORCE_KEYS_LIST);
        }
        return TextSizeKey.getKeys().get(stringKey);
    }


    private Path getPathToFile(Scanner scanner) {
        Path pathToFile = Path.of(scanner.nextLine());

        while (!Files.isRegularFile(pathToFile)) {
            System.out.println(FILE_NOT_EXIST_WARNING);
            pathToFile = Path.of(scanner.nextLine());
        }
        return pathToFile;
    }

    private boolean isValidCipherKeyRange(int key){
        return key > 0 && key < ALPHABET_SIZE;
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
