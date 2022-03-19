package com.javarush.caesarcipher.dialog;

public class ConsoleText {
    public static final String HELLO_TEXT =
            """
             ************************
                   Шифр Цезаря.
                 Добро пожаловать!
             ************************
             """;
    public static final String ENTER_CIPHER_KEY_MESSAGE = "Введите ключ шифрования, от 1 до ";
    public static final String CHOISE_OF_ACTION_MESSAGE = "Выберите действие из представленных";
    public static final String ENTER_PATH_TO_DECRYPTED_FILE_MESSAGE = "Введите путь к незашифрованному файлу...";
    public static final String ENTER_PATH_TO_ENCRYPTED_FILE_MESSAGE = "Введите путь к зашифрованному файлу...";
    public static final String FILE_NOT_EXIST_WARNING = "Такого файла не существует, проверьте путь и имя файла!";
    public static final String RESULT_ACTION_MESSAGE = "Результат находится в файле ";
    public static final String ENCRYPTION_SELECTED_MESSAGE = "Выбрано шифрование файла!";
    public static final String DECRYPTION_SELECTED_MESSAGE = "Расшифровываем файл по ключу!";
    public static final String BRUTE_FORCE_SELECTED_MESSAGE = "Расшифровываем файл подбором!";
    public static final String CHOISE_OF_BRUTE_FORCE_KEY_MESSAGE = "Выберите размер текста для повышения точности подбора";
    public static final String ENCRYPT_ACTION_KEY = "E";
    public static final String DECRYPT_ACTION_KEY = "D";
    public static final String BRUTE_FORCE_ACTION_KEY = "B";
    public static final String SMALL_TEXT_KEY = "S";
    public static final String MEDIUM_TEXT_KEY = "M";
    public static final String LARGE_TEXT_KEY = "XL";

}
