package com.javarush.caesarcipher.dialog;

import java.util.HashMap;
import java.util.Map;

public enum ActionKey {
    ENCRYPT("E", " - зашифровать текст файла\n", "Выбрано шифрование файла!"),
    DECRYPT("D", " - расшифровать текст файла по ключу\n", "Расшифровываем текст файла по ключу!"),
    BRUTE_FORCE("B", " - расшифровать текст подбором (brute force)", "Расшифровываем текст файла подбором!");

    private final String actionKey;
    private final String actionDescription;
    private final String actionMessage;

    ActionKey(String actionKey, String actionDescription, String actionMessage) {
        this.actionKey = actionKey;
        this.actionDescription = actionDescription;
        this.actionMessage = actionMessage;
    }

    public static Map<String, ActionKey> getActionKeyAndActionName() {
        Map<String, ActionKey> keyAndNameAction = new HashMap<>();
        for (ActionKey value : values()) {
            keyAndNameAction.put(value.getActionKey(), value);
        }
        return keyAndNameAction;
    }

    public String getActionKey() {
        return actionKey;
    }

    public String getActionMessage() {
        return actionMessage;
    }

    public String getActionDescription() {
        return actionDescription;
    }
}
