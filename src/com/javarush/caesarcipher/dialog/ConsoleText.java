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
    public static final String ENTER_PATH_TO_INPUT_FILE_MESSAGE = "Введите путь к текстовому файлу из которого читаем...";
    public static final String ENTER_PATH_TO_OUTPUT_FILE_MESSAGE = "Введите путь к текстовому файлу в который записываем...";
    public static final String FILE_NOT_EXIST_WARNING = "Такого текстового файла не существует!\nПроверьте путь, имя файла и его расширение.";
    public static final String RESULT_ACTION_MESSAGE = "Результат находится в файле ";
    public static final String ENCRYPTION_SELECTED_MESSAGE = "Выбрано шифрование файла!";
    public static final String DECRYPTION_SELECTED_MESSAGE = "Расшифровываем текст файла по ключу!";
    public static final String BRUTE_FORCE_SELECTED_MESSAGE = "Расшифровываем текст файла подбором!";
    public static final String CHOISE_OF_BRUTE_FORCE_KEY_MESSAGE = "Выберите размер текста для повышения точности подбора";

}
