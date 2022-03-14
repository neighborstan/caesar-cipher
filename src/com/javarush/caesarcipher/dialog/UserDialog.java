package com.javarush.caesarcipher.dialog;

import com.javarush.caesarcipher.cipher.CaesarCipher;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import static com.javarush.caesarcipher.dialog.ConsoleText.*;

public class UserDialog {
    public static final String ENCRYPT = "E";
    public static final String DECRYPT = "D";
    public static final String BRUTE_FORCE = "B";

    public static final int alphabetSize = CaesarCipher.alphabetSize();

    private Path pathToDecryptedFile;
    private Path pathToEncryptedFile;
    private int cipherKey;
    private int bruteForceKey;


    public void start() throws IOException {
        System.out.println(HELLO_TEXT);
        System.out.println(CHOISE_OF_ACTION);
        System.out.println(ACTIONS_LIST_TEXT);

        try (Scanner scanner = new Scanner(System.in)) {
            String action;
            while (true) {
                action = scanner.nextLine();
                if (action.equals(ENCRYPT) || action.equals(DECRYPT) || action.equals(BRUTE_FORCE)) {
                    break;
                }
                System.out.println(CHOISE_OF_ACTION);
            }

            if (action.equals(ENCRYPT)) {
                System.out.println(ENCRYPTION_SELECTED);
                System.out.println(ENTER_PATH_TO_DECRYPTED_FILE);
                pathToDecryptedFile = getPathToDecryptedFile(scanner);

                System.out.println(ENTER_PATH_TO_ENCRYPTED_FILE);
                pathToEncryptedFile = getPathToEncryptedFile(scanner);

                System.out.println(ENTER_CIPHER_KEY);
                cipherKey = getValidCipherKey(scanner);
                CaesarCipher.encrypt(pathToDecryptedFile, pathToEncryptedFile, cipherKey);

                System.out.println(RESULT_ACTION + pathToEncryptedFile.toAbsolutePath());

            } else if (action.equals(DECRYPT)) {
                System.out.println(DECRYPTION_SELECTED);
                System.out.println(ENTER_PATH_TO_ENCRYPTED_FILE);
                pathToEncryptedFile = getPathToEncryptedFile(scanner);

                System.out.println(ENTER_PATH_TO_DECRYPTED_FILE);
                pathToDecryptedFile = getPathToDecryptedFile(scanner);

                System.out.println(ENTER_CIPHER_KEY);
                cipherKey = getValidCipherKey(scanner);
                CaesarCipher.decrypt(pathToEncryptedFile, pathToDecryptedFile, cipherKey);

                System.out.println(ConsoleText.RESULT_ACTION + pathToDecryptedFile.toAbsolutePath());

            } else if (action.equals(BRUTE_FORCE)) {
                System.out.println(BRUTE_FORCE_SELECTED);
                System.out.println(ENTER_PATH_TO_ENCRYPTED_FILE);
                pathToEncryptedFile = getPathToEncryptedFile(scanner);

                System.out.println(ENTER_PATH_TO_DECRYPTED_FILE);
                pathToDecryptedFile = getPathToDecryptedFile(scanner);

                System.out.println(CHOISE_OF_BRUTE_FORCE_KEY);
                System.out.println(BRUTE_FORCE_KEYS_LIST);
                bruteForceKey = getBruteForceKey(scanner);
                CaesarCipher.bruteForce(pathToEncryptedFile, pathToDecryptedFile, bruteForceKey);

                System.out.println(ConsoleText.RESULT_ACTION + pathToDecryptedFile.toAbsolutePath());
            }
        }
    }

    private int getValidCipherKey(Scanner scanner) {
        int key;
        while (true) {
            try {
                key = Integer.parseInt(scanner.nextLine());
                if (key < 1 || key >= alphabetSize) throw new NumberFormatException();
                break;
            } catch (NumberFormatException e) {
                System.out.println(ENTER_CIPHER_KEY);
            }
        }
        return key;
    }

    private int getBruteForceKey(Scanner scanner) {
        int bruteForceKey;
        while (true) {
            try {
                String stringKey = scanner.nextLine();
                bruteForceKey = switch (stringKey) {
                    case "S" -> 2;
                    case "M" -> 5;
                    case "XL" -> 8;
                    default -> throw new NumberFormatException();
                };

                break;
            } catch (NumberFormatException e) {
                System.out.println(CHOISE_OF_BRUTE_FORCE_KEY);
            }
        }
        return bruteForceKey;
    }

    private Path getPathToEncryptedFile(Scanner scanner) {
        Path pathToEncryptedFile = Path.of(scanner.nextLine());

        while (!Files.isRegularFile(pathToEncryptedFile)) {
            System.out.println(FILE_NOT_EXIST_WARNING);
            pathToEncryptedFile = Path.of(scanner.nextLine());
        }
        return pathToEncryptedFile;
    }

    private Path getPathToDecryptedFile(Scanner scanner) {
        Path pathToDecryptedFile = Path.of(scanner.nextLine());

        while (!Files.isRegularFile(pathToDecryptedFile)) {
            System.out.println(FILE_NOT_EXIST_WARNING);
            pathToDecryptedFile = Path.of(scanner.nextLine());
        }
        return pathToDecryptedFile;
    }
}
