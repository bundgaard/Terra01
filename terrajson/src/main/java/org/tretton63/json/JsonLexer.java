package org.tretton63.json;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.StringReader;

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
            if (r == 0 ) {
                return new JsonToken(JsonToken.Type.Eof, "<EOF>");
            }

            if (r == '[') {
                return new JsonToken(JsonToken.Type.ArrayOpen, "[");
            } else if (r == ']') {
                return new JsonToken(JsonToken.Type.ArrayClosed, "]");
            } else if (r == '-' || Character.isDigit(r)) {
                return readNumber();
            } else if (r == ',') {
                return new JsonToken(JsonToken.Type.Comma, ",");
            }
        }
    }

    private JsonToken readNumber() {
        StringBuilder out = new StringBuilder();
        while(Character.isDigit(last)) {

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
            last = (char)r;
            return (char) r;
        } catch (IOException ignored) {
            return 0;
        }
    }

    private void back(char r) {
        peeking = true;
        peekChar = r;
    }


}
