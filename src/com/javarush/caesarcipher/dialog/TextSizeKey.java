package com.javarush.caesarcipher.dialog;

import java.util.HashMap;
import java.util.Map;

public enum TextSizeKey {
    SMALL("S", 2),
    MEDIUM("M", 5),
    LARGE("XL", 8);

    private final String stringKey;
    private final int key;

    private static final Map<String, Integer> KEYS = new HashMap<>() {{
        for (TextSizeKey value : TextSizeKey.values()) {
            put(value.stringKey, value.key);
        }
    }};

    TextSizeKey(String stringKey, int key) {
        this.stringKey = stringKey;
        this.key = key;
    }

    public String getStringKey() {
        return stringKey;
    }

    public static Map<String, Integer> getKeys() {
        return KEYS;
    }


}
