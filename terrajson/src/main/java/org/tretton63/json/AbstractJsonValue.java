package org.tretton63.json;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class AbstractJsonValue implements JsonValue {
    private final Type type;

    public AbstractJsonValue(Type type) {
        this.type = type;
    }

    public boolean isArray() {
        return type == Type.Array;
    }

    public boolean isNumber() {
        return type == Type.Number;
    }

    public boolean isObject() {
        return type == Type.Object;
    }

    public Type typeInfo() {
        return type;
    }

    public boolean isBoolean() {
        return type == Type.Boolean;
    }

    public boolean isString() {
        return type == Type.String;
    }

    public static class Array extends AbstractJsonValue {
        private final ArrayList<JsonValue> items = new ArrayList<>();

        public Array() {
            super(Type.Array);

        }

        public void append(JsonValue value) {
            items.add(value);
        }

        public JsonValue get(int i) {
            return items.get(i);
        }

        public int size() {
            return items.size();
        }


        public ArrayList<JsonValue> toList() {
            return items;
        }
    }


    public static class JsonNumber extends AbstractJsonValue {
        private final String value;
        private final JsonToken token;

        public JsonNumber(JsonToken token, String value) {
            super(Type.Number);
            this.token = token;
            this.value = value;
        }

        public int intValue() {
            return Integer.parseInt(value);
        }

    }

    public static class JsonObject extends AbstractJsonValue {

        private final HashMap<JsonValue, JsonValue> items = new HashMap<>();

        public JsonObject() {
            super(Type.Object);

        }

        public void add(JsonValue key, JsonValue val) {
            items.put(key, val);
        }

        public int size() {
            return items.size();
        }

        public HashMap<JsonValue, JsonValue> toMap() {
            return items;
        }

        @Override
        public JsonObject asObject() {
            return this;
        }

    }

    public static class JsonString extends AbstractJsonValue{

        private final String value;
        public JsonString(String value) {
            super(Type.String);
            this.value = value;
        }
        @Override
        public JsonString asString() {
            return this;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "JsonString{" +
                    "value='" + value + '\'' +
                    '}';
        }
    }

}
