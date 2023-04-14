package org.tretton63.json;

public interface JsonValue {
    enum Type {
        Error,
        Eof,
        Object, Array, String, Number, True, False, Null
    }

    JsonArray asArray();

    JsonNumber asNumber();


    JsonObject asObject();


}
