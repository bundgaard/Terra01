package org.tretton63.json;

public interface JsonValue {
    enum Type {
        Error("Error"),
        Eof("Eof"),
        Object("Object"),
        Array("Array"),
        String("String"),
        Number("Number"),
        Boolean("Boolean"),
        Null("Null");

        private final String name;

        Type(String name) {
            this.name = name;
        }

        @Override
        public java.lang.String toString() {
            return "Type{%s}".formatted(name);
        }
    }

    default AbstractJsonValue.Array asArray() {
        return (AbstractJsonValue.Array) this;
    }

    default AbstractJsonValue.JsonNumber asNumber() {
        return (AbstractJsonValue.JsonNumber) this;
    }

    default AbstractJsonValue.JsonString asString() {
        return (AbstractJsonValue.JsonString)this;
    }

    default AbstractJsonValue.JsonObject asObject() {
        return (AbstractJsonValue.JsonObject) this;
    }

    default boolean isNumber() {
        return false;
    }

    default boolean isArray() {
        return false;
    }

    default boolean isObject() {
        return false;
    }

    default boolean isBoolean() {
        return false;
    }

    default boolean isString() {
        return false;
    }


    Type typeInfo();

}
