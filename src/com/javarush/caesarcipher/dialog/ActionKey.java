package com.javarush.caesarcipher.dialog;

public enum ActionKey {
    ENCRYPT("E", ConsoleText.ENCRYPTION_SELECTED_MESSAGE),
    DECRYPT("D", ConsoleText.DECRYPTION_SELECTED_MESSAGE),
    BRUTE_FORCE("B", ConsoleText.BRUTE_FORCE_SELECTED_MESSAGE);

    private final String actionKey;
    private final String actionMessage;

    ActionKey(String actionKey, String actionMessage) {
        this.actionKey = actionKey;
        this.actionMessage = actionMessage;
    }

    public String getActionKey() {
        return actionKey;
    }

    public String getActionMessage() {
        return actionMessage;
    }
}
