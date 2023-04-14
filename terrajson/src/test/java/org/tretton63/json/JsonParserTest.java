package org.tretton63.json;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class JsonParserTest {

    @Test
    void parse() {
        var parser = new JsonParser("""
                [100,2,3,[400,401,402],"Hello,❤ World"]""");
        var value = parser.parse();

        var items = value.asArray().toList();
        for (var item : items) {
            System.out.println(item.typeInfo());
            switch (item.typeInfo()) {
                case Array -> item.asArray().toList().stream()
                        .map(JsonValue::asNumber)
                        .map(AbstractJsonValue.JsonNumber::intValue)
                        .forEach(System.out::println);

                case Number -> System.out.println(item.asNumber().intValue());
                case String -> {
                    System.out.println(item.asString().getValue());
                }
                default -> throw new IllegalStateException("Unexpected value: " + item.typeInfo());
            }

        }
    }

    @Test
    void parse_object() {
        var parser = new JsonParser("""
                {"foo": [-1, 2 , 3 , 4]}""");
        var value = parser.parse();

        var obj = value.asObject().toMap();

        System.out.println("Got an Object");
        obj.forEach((key, val) -> {
            if (Objects.requireNonNull(val.typeInfo()) == JsonValue.Type.Array) {
                val.asArray().toList().stream().map(JsonValue::asNumber).map(AbstractJsonValue.JsonNumber::intValue).forEach(System.out::println);
            } else {
                System.out.printf("%s %s\n", key, val.typeInfo());
            }

        });

    }
}