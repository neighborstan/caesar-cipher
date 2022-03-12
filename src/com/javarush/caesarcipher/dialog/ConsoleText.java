package com.javarush.caesarcipher.dialog;

public class ConsoleText {
    public static final String HELLO_TEXT =
            """
             ************************
                   Шифр Цезаря.
                 Добро пожаловать!
             ************************
             """;
    public static final String ENTER_CIPHER_KEY_MESSAGE = "Введите ключ шифрования, от 1 до " + (UserDialog.alphabetSize - 1) + " включительно...";
    public static final String CHOISE_OF_ACTION_MESSAGE = "Выберите действие из представленных:";
    public static final String ENTER_PATH_TO_DECRYPTED_FILE = "Введите путь к расшифрованному файлу...";
    public static final String ENTER_PATH_TO_ENCRYPTED_FILE = "Введите путь к зашифрованному файлу...";
    public static final String FILE_NOT_EXIST_WARNING = "Такого файла не существует, проверьте путь и имя файла!";
    public static final String TEXT_ENCRYPTED_MESSAGE = "Текст зашифрован и находится в файле ";
    public static final String TEXT_DECRYPTED_MESSAGE = "Текст расшифрован и находится в файле ";
    public static final String ENCRYPT_CHOISE_TEXT = "Выбрано шифрование файла!";
    public static final String DECRYPT_KEY_CHOISE_TEXT = "Расшифровываем файл по ключу!";
    public static final String DECRYPT_BRUTE_FORCE_CHOISE_TEXT = "Расшифровываем файл подбором!";
    public static final String ACTIONS_LIST_TEXT = "" +
            UserDialog.ENCRYPT + " - зашифровать файл\n" +
            UserDialog.DECRYPT + " - расшифровать файл по ключу\n" +
            UserDialog.BRUTE_FORCE + " - расшифровать файл подбором (brute force)";
}
