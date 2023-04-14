package org.tretton63.json;

public class JsonToken {

    private final String value;

    private final Type type;

    public enum Type {
        ArrayOpen, ArrayClosed, ObjectOpen, ObjectClosed, Digit, Comma, String, Boolean, Null, Eof, Colon,
    }


    public JsonToken(Type type, String value) {

        this.type = type;
        this.value = value;
    }

    public JsonToken() {
        this(Type.Null, "Null");
    }


    public String getValue() {
        return value;
    }

    public Type getType() {
        return type;
    }


    @Override
    public String toString() {
        return "Token{" +
                "value='" + value + '\'' +
                ", type=" + type +
                '}';
    }
}
