package com.javarush.caesarcipher.dialog;

import com.javarush.caesarcipher.exceptions.CaesarCipherIOException;
import com.javarush.caesarcipher.cipher.CaesarCipher;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import static com.javarush.caesarcipher.dialog.ActionKey.*;
import static com.javarush.caesarcipher.dialog.ConsoleText.*;

public class UserDialog {
    private final String BRUTE_FORCE_KEYS_LIST = getBruteForceKeysList();
    private final String ACTIONS_LIST_TEXT = getActionsDescription();

    private final CaesarCipher CAESAR_CIPHER = CaesarCipher.getInstance();

    private final int ALPHABET_SIZE = CAESAR_CIPHER.alphabetSize();


    public void start() {
        System.out.println(HELLO_TEXT);

        try (Scanner scanner = new Scanner(System.in)) {
            actionRun(scanner);
        } catch (CaesarCipherIOException e) {
            System.err.println(e);
        }
    }

    private void actionRun(Scanner scanner) {
        String actionKey = getActionKeyAsString(scanner);
        ActionKey action = ActionKey.getActionKeyAndActionName().get(actionKey);

        System.out.println(action.getActionMessage());

        Path inputFilePath = getPathToFile(ENTER_PATH_TO_INPUT_FILE_MESSAGE, scanner);
        Path outputFilePath = getPathToFile(ENTER_PATH_TO_OUTPUT_FILE_MESSAGE, scanner);

        if (ENCRYPT.getActionKey().equals(actionKey)) {
            int cipherKey = getValidCipherKey(scanner);

            CAESAR_CIPHER.encrypt(inputFilePath, outputFilePath, cipherKey);

        } else if (DECRYPT.getActionKey().equals(actionKey)) {
            int cipherKey = getValidCipherKey(scanner);

            CAESAR_CIPHER.decrypt(inputFilePath, outputFilePath, cipherKey);

        } else if (BRUTE_FORCE.getActionKey().equals(actionKey)) {
            int bruteForceTextKey = getBruteForceTextKey(scanner);

            CAESAR_CIPHER.bruteForce(inputFilePath, outputFilePath, bruteForceTextKey);
        }

        System.out.println(RESULT_ACTION_MESSAGE + outputFilePath.toAbsolutePath());
    }

    private String getBruteForceKeysList() {
        StringBuilder builder = new StringBuilder();
        for (TextSizeKey value : TextSizeKey.values()) {
            builder.append(value.getStringKey()).append(value.getDescription());
        }
        return builder.toString();
    }

    private String getActionsDescription() {
        StringBuilder builder = new StringBuilder();
        for (ActionKey value : ActionKey.values()) {
            builder.append(value.getActionKey()).append(value.getActionDescription());
        }
        return builder.toString();
    }

    private boolean isActionKeyValid(String actionKey) {
        return ENCRYPT.getActionKey().equals(actionKey) || DECRYPT.getActionKey().equals(actionKey) || BRUTE_FORCE.getActionKey().equals(actionKey);
    }

    private String getActionKeyAsString(Scanner scanner) {
        String action;
        do {
            System.out.println(CHOOSE_OF_ACTION_MESSAGE);
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
        System.out.println(CHOOSE_OF_BRUTE_FORCE_KEY_MESSAGE);
        System.out.println(BRUTE_FORCE_KEYS_LIST);

        while (!TextSizeKey.getKeys().containsKey(stringKey = scanner.nextLine())) {
            System.out.println(CHOOSE_OF_BRUTE_FORCE_KEY_MESSAGE);
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
