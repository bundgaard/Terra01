package org.tretton63.json;

class JsonLexerTest {

    enum foo {
        bar, baz, boo
    }
    @org.junit.jupiter.api.Test
    void nextToken() {
        var lexer = new JsonLexer("[1,2,3,4]");
        JsonToken token = lexer.nextToken();
        while(token.getType() != JsonToken.Type.Eof){
            System.out.println(token);
            token = lexer.nextToken();
        }


    }
}