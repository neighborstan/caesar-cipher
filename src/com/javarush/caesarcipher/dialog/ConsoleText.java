package com.javarush.caesarcipher.dialog;

public class ConsoleText {
    public static final String HELLO_TEXT =
            """
             ************************
                   Шифр Цезаря.
                 Добро пожаловать!
             ************************
             """;
    public static final String ENTER_CIPHER_KEY = "Введите ключ шифрования, от 1 до " + (UserDialog.alphabetSize - 1) + " включительно...";
    public static final String CHOISE_OF_ACTION = "Выберите действие из представленных";
    public static final String ENTER_PATH_TO_DECRYPTED_FILE = "Введите путь к расшифрованному файлу...";
    public static final String ENTER_PATH_TO_ENCRYPTED_FILE = "Введите путь к зашифрованному файлу...";
    public static final String FILE_NOT_EXIST_WARNING = "Такого файла не существует, проверьте путь и имя файла!";
    public static final String RESULT_ACTION = "Результат находится в файле ";
    public static final String ENCRYPTION_SELECTED = "Выбрано шифрование файла!";
    public static final String DECRYPTION_SELECTED = "Расшифровываем файл по ключу!";
    public static final String BRUTE_FORCE_SELECTED = "Расшифровываем файл подбором!";
    public static final String CHOISE_OF_BRUTE_FORCE_KEY = "Выберите размер текста для повышения точности подбора";
    public static final String BRUTE_FORCE_KEYS_LIST = "S - небольшой текст\nM - средний текст\nXL - большой текст";
    public static final String ACTIONS_LIST_TEXT = "" +
            UserDialog.ENCRYPT + " - зашифровать файл\n" +
            UserDialog.DECRYPT + " - расшифровать файл по ключу\n" +
            UserDialog.BRUTE_FORCE + " - расшифровать файл подбором (brute force)";
}
