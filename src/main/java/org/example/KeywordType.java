package org.example;

public enum KeywordType {
    CONTENT, AUTHOR, ERROR;

    static KeywordType create(String string) {
        KeywordType type = KeywordType.ERROR;
        try {
            type = KeywordType.valueOf(string.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
        return type;
    }
}
