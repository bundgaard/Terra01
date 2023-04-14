package org.tretton63.json;

public abstract class AbstractJsonValue implements JsonValue {
    private final Type type;
    public AbstractJsonValue(Type type)    {
        this.type = type;
    }
}
