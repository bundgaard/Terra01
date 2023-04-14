package org.tretton63.json;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JsonParserTest {

    @Test
    void parse() {
        var parser = new JsonParser("[100,2,3,[400,401,402]]");
        var value = parser.parse();

        var items = value.asArray().toList();
        for(var item : items) {
            System.out.println(item.asNumber().intValue());
        }
    }
}