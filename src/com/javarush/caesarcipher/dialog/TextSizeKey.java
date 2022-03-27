package com.javarush.caesarcipher.dialog;

import java.util.HashMap;
import java.util.Map;

public enum TextSizeKey {
    SMALL("S", 2, " - небольшой текст\n"),
    MEDIUM("M", 5, " - средний текст\n"),
    LARGE("XL", 8, " - большой текст");

    private final String description;
    private final String stringKey;
    private final int key;

    private static final Map<String, Integer> KEYS = new HashMap<>() {{
        for (TextSizeKey value : TextSizeKey.values()) {
            put(value.stringKey, value.key);
        }
    }};

    TextSizeKey(String stringKey, int key, String description) {
        this.stringKey = stringKey;
        this.key = key;
        this.description = description;
    }

    public String getStringKey() {
        return stringKey;
    }

    public static Map<String, Integer> getKeys() {
        return KEYS;
    }

    public String getDescription() {
        return description;
    }
}
