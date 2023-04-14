package org.tretton63.json;

import org.jetbrains.annotations.NotNull;

public class JsonParser {

    private final JsonLexer lexer;
    private JsonToken currentToken;
    private JsonToken nextToken;

    public JsonParser(String jsonString) {
        lexer = new JsonLexer(jsonString);
        nextToken();
        nextToken();
    }

    public JsonValue parse() {
        if (currentToken.getType() == JsonToken.Type.ArrayOpen) {
            return parseArray();
        } else if (currentToken.getType() == JsonToken.Type.Digit) {
            return parseNumber();
        } else if (currentToken.getType() == JsonToken.Type.ObjectOpen) {
            return parseObject();
        }

        return null;
    }

    private JsonValue parseObject() {
        var object = new JsonObject();

        return object;
    }

    private JsonValue parseNumber() {
        var token = currentToken;
        nextToken();
        return new JsonNumber(token, token.getValue());
    }

    private @NotNull JsonValue parseArray() {
        var items = new JsonArray();
        consume(JsonToken.Type.ArrayOpen); // Eat the [

        while (currentToken.getType() != JsonToken.Type.ArrayClosed) {
            items.append(parse());
            var token = currentToken;
            if (token.getType() == JsonToken.Type.Comma) {
                nextToken();
            }
        }
        consume(JsonToken.Type.ArrayClosed); // Eat the ]

        return items;
    }

    private void nextToken() {
        currentToken = nextToken;
        nextToken = lexer.nextToken();
    }

    private void consume(JsonToken.Type type) {
        if (currentToken.getType() == type) {
            nextToken();
        }
    }
}
