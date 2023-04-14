package org.tretton63.json;

import org.jetbrains.annotations.NotNull;

import static org.tretton63.json.JsonToken.Type.Comma;

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
        } else if (currentToken.getType() == JsonToken.Type.String) {
            return parseString();
        }

        return null;
    }

    private JsonValue parseString() {
        var token = currentToken.getValue();
        nextToken();
        return new AbstractJsonValue.JsonString(token);
    }

    private JsonValue parseObject() {
        var object = new AbstractJsonValue.JsonObject();

        while (currentToken.getType() != JsonToken.Type.ObjectClosed) {
            nextToken();
            var key = parse();
            System.out.println("key:" +key);
            nextToken(); // Eat :
            var value = parse();
            object.add(key, value);
            System.out.println("value " + value);
            System.out.println("after value parse");
            System.out.printf("current token %s\n", currentToken);
            System.out.printf("next token %s\n", nextToken);

        }

        System.out.printf("parseObject %d\n", object.size());

        return object;
    }

    private JsonValue parseNumber() {
        var token = currentToken;
        nextToken();
        return new AbstractJsonValue.JsonNumber(token, token.getValue());
    }

    private @NotNull JsonValue parseArray() {
        var items = new AbstractJsonValue.Array();
        consume(JsonToken.Type.ArrayOpen); // Eat the [

        while (currentToken.getType() != JsonToken.Type.ArrayClosed) {
            items.append(parse());
            var token = currentToken;
            if (token.getType() == Comma) {
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

    private boolean peekTokenIs(JsonToken.Type tokenType) {
        return nextToken.getType() == tokenType;
    }

    private boolean expectPeek(JsonToken.Type tokenType) {
        if (peekTokenIs(tokenType)) {
            nextToken();
            return true;
        }
        return false;
    }
}
