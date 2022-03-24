package com.javarush.caesarcipher.dialog;

import com.javarush.caesarcipher.exceptions.CaesarCipherIOException;
import com.javarush.caesarcipher.cipher.CaesarCipher;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import static com.javarush.caesarcipher.dialog.ActionKey.*;
import static com.javarush.caesarcipher.dialog.ConsoleText.*;

public class UserDialog {
    private final String BRUTE_FORCE_KEYS_LIST = "%s - небольшой текст\n%s - средний текст\n%s - большой текст"
            .formatted(
                    TextSizeKey.SMALL.getStringKey(),
                    TextSizeKey.MEDIUM.getStringKey(),
                    TextSizeKey.LARGE.getStringKey());

    private final String ACTIONS_LIST_TEXT = "%s - зашифровать текст файла\n%s - расшифровать текст файла по ключу\n%s - расшифровать текст подбором (brute force)"
            .formatted(
                    ENCRYPT.getActionKey(),
                    DECRYPT.getActionKey(),
                    BRUTE_FORCE.getActionKey());

    private final CaesarCipher CAESAR_CIPHER = CaesarCipher.getInstance();

    private final int ALPHABET_SIZE = CAESAR_CIPHER.alphabetSize();


    public void start() {
        System.out.println(HELLO_TEXT);

        try (Scanner scanner = new Scanner(System.in)) {
            Path inputFilePath;
            Path outputFilePath;
            int cipherKey;

            String actionKey = getActionKeyAsString(scanner);

            if (ENCRYPT.getActionKey().equals(actionKey)) {
                System.out.println(ENCRYPT.getActionMessage());

                inputFilePath = getPathToFile(ENTER_PATH_TO_INPUT_FILE_MESSAGE, scanner);
                outputFilePath = getPathToFile(ENTER_PATH_TO_OUTPUT_FILE_MESSAGE, scanner);
                cipherKey = getValidCipherKey(scanner);

                CAESAR_CIPHER.encrypt(inputFilePath, outputFilePath, cipherKey);

                System.out.println(RESULT_ACTION_MESSAGE + outputFilePath.toAbsolutePath());

            } else if (DECRYPT.getActionKey().equals(actionKey)) {
                System.out.println(DECRYPT.getActionMessage());

                inputFilePath = getPathToFile(ENTER_PATH_TO_INPUT_FILE_MESSAGE, scanner);
                outputFilePath = getPathToFile(ENTER_PATH_TO_OUTPUT_FILE_MESSAGE, scanner);
                cipherKey = getValidCipherKey(scanner);

                CAESAR_CIPHER.decrypt(inputFilePath, outputFilePath, cipherKey);

                System.out.println(RESULT_ACTION_MESSAGE + outputFilePath.toAbsolutePath());

            } else if (BRUTE_FORCE.getActionKey().equals(actionKey)) {
                System.out.println(BRUTE_FORCE.getActionMessage());

                inputFilePath = getPathToFile(ENTER_PATH_TO_INPUT_FILE_MESSAGE, scanner);
                outputFilePath = getPathToFile(ENTER_PATH_TO_OUTPUT_FILE_MESSAGE, scanner);
                int bruteForceTextKey = getBruteForceTextKey(scanner);

                CAESAR_CIPHER.bruteForce(inputFilePath, outputFilePath, bruteForceTextKey);

                System.out.println(RESULT_ACTION_MESSAGE + outputFilePath.toAbsolutePath());
            }
        } catch (CaesarCipherIOException e) {
            System.err.println(e);
        }
    }

    private boolean isActionKeyValid(String actionKey) {
        return ENCRYPT.getActionKey().equals(actionKey) || DECRYPT.getActionKey().equals(actionKey) || BRUTE_FORCE.getActionKey().equals(actionKey);
    }

    private String getActionKeyAsString(Scanner scanner) {
        String action;
        do {
            System.out.println(CHOISE_OF_ACTION_MESSAGE);
            System.out.println(ACTIONS_LIST_TEXT);

            action = scanner.nextLine();
        } while (!isActionKeyValid(action));

        return action;
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


    private Path getPathToFile(String message, Scanner scanner) {
        System.out.println(message);

        Path pathToFile = Path.of(scanner.nextLine());

        while (!Files.isRegularFile(pathToFile) || !isTxtFileExtension(pathToFile)) {
            System.err.println(FILE_NOT_EXIST_WARNING);
            pathToFile = Path.of(scanner.nextLine());
        }
        return pathToFile;
    }

    private boolean isValidCipherKeyRange(int key) {
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

    private boolean isTxtFileExtension(Path path) {
        return path.toString().trim().endsWith("txt");
    }
}
