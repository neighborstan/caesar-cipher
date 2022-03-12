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


    public void start() throws IOException {
        System.out.println(HELLO_TEXT);
        System.out.println(CHOISE_OF_ACTION_MESSAGE);
        System.out.println(ACTIONS_LIST_TEXT);

        try (Scanner scanner = new Scanner(System.in)) {
            String action;
            while (true) {
                action = scanner.nextLine();
                if (action.equals(ENCRYPT) || action.equals(DECRYPT) || action.equals(BRUTE_FORCE)) {
                    break;
                }
                System.out.println(CHOISE_OF_ACTION_MESSAGE);
            }

            if (action.equals(ENCRYPT)) {
                System.out.println(ENCRYPT_CHOISE_TEXT);
                System.out.println(ENTER_PATH_TO_DECRYPTED_FILE);
                pathToDecryptedFile = getPathToDecryptedFile(scanner);

                System.out.println(ENTER_PATH_TO_ENCRYPTED_FILE);
                pathToEncryptedFile = getPathToEncryptedFile(scanner);

                System.out.println(ENTER_CIPHER_KEY_MESSAGE);
                cipherKey = getValidCipherKey(scanner);
                CaesarCipher.encrypt(pathToDecryptedFile, pathToEncryptedFile, cipherKey);

                System.out.println(TEXT_ENCRYPTED_MESSAGE + pathToEncryptedFile.toAbsolutePath());

            } else if (action.equals(DECRYPT)) {
                System.out.println(DECRYPT_KEY_CHOISE_TEXT);
                System.out.println(ENTER_PATH_TO_ENCRYPTED_FILE);
                pathToEncryptedFile = getPathToEncryptedFile(scanner);

                System.out.println(ENTER_PATH_TO_DECRYPTED_FILE);
                pathToDecryptedFile = getPathToDecryptedFile(scanner);

                System.out.println(ENTER_CIPHER_KEY_MESSAGE);
                cipherKey = getValidCipherKey(scanner);
                CaesarCipher.decrypt(pathToEncryptedFile, pathToDecryptedFile, cipherKey);

                System.out.println(TEXT_DECRYPTED_MESSAGE + pathToDecryptedFile.toAbsolutePath());

            } else if (action.equals(BRUTE_FORCE)) {
                System.out.println(DECRYPT_BRUTE_FORCE_CHOISE_TEXT);
                System.out.println(ENTER_PATH_TO_ENCRYPTED_FILE);
                pathToEncryptedFile = getPathToEncryptedFile(scanner);

                System.out.println(ENTER_PATH_TO_DECRYPTED_FILE);
                pathToDecryptedFile = getPathToDecryptedFile(scanner);
                CaesarCipher.bruteForce(pathToEncryptedFile, pathToDecryptedFile);

                System.out.println(TEXT_DECRYPTED_MESSAGE + pathToDecryptedFile.toAbsolutePath());
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
                System.out.println(ENTER_CIPHER_KEY_MESSAGE);
            }
        }
        return key;
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
