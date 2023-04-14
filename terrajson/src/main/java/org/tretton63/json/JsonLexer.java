package org.tretton63.json;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.StringReader;

import static org.tretton63.json.JsonToken.Type.*;

public class JsonLexer {
    private final PushbackReader pushbackReader;
    private boolean peeking;
    private char peekChar;
    private char last;

    public JsonLexer(String jsonString) {
        pushbackReader = new PushbackReader(new StringReader(jsonString));
    }


    public JsonToken nextToken() {

        while (true) {
            var r = read();
            if (Character.isSpaceChar(r)) {
                continue;
            }
            if (r == 0) {
                return new JsonToken(Eof, "<EOF>");
            }

            if (r == '{') {
                return new JsonToken(ObjectOpen, "{");
            } else if (r == '}') {
                return new JsonToken(ObjectClosed, "}");
            } else if (r == '[') {
                return new JsonToken(ArrayOpen, "[");
            } else if (r == ']') {
                return new JsonToken(ArrayClosed, "]");
            } else if (r == '-' || Character.isDigit(r)) {
                return readNumber();
            } else if (r == ',') {
                return new JsonToken(Comma, ",");
            } else if (r == '"') {
                return readString();
            } else if (r == ':') {
                return new JsonToken(Colon, ":");
            } else if (r == 't') {
                return readTrue();
            } else if (r == 'f') {
                return readFalse();
            } else if (r == 'n') {
                return readNull();
            }
        }
    }

    public static final JsonToken TRUE = new JsonToken(JsonToken.Type.Boolean, "true");
    public static final JsonToken FALSE = new JsonToken(JsonToken.Type.Boolean, "false");
    public static final JsonToken NULL = new JsonToken(Null, "null");

    private JsonToken readTrue() {

        return TRUE;
    }

    private JsonToken readFalse() {
        return FALSE;
    }

    private JsonToken readNull() {
        return NULL;
    }

    private JsonToken readString() {

        var out = new StringBuilder();
        consumeChar('"');
        while (last != '"') {
            out.append(last);
            read();
        }

        return new JsonToken(JsonToken.Type.String, out.toString());
    }

    private JsonToken readNumber() {
        StringBuilder out = new StringBuilder();
        out.append(last);
        readChar();
        while (Character.isDigit(last)) {

            out.append(last);
            read();
        }
        back(last);
        // accum the digits and return the Token
        return new JsonToken(JsonToken.Type.Digit, out.toString());
    }

    private char read() {
        if (peeking) {
            peeking = false;
            return peekChar;
        }
        return readChar();
    }

    private char readChar() {

        try {
            var r = pushbackReader.read();
            if (r == -1) {
                return 0;
            }
            last = (char) r;
            return (char) r;
        } catch (IOException ignored) {
            return 0;
        }
    }

    private void back(char r) {
        peeking = true;
        peekChar = r;
    }

    private void consumeChar(char r) {
        if (r == last) {
            last = readChar();
        }
    }
}
