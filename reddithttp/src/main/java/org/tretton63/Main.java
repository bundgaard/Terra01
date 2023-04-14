package org.tretton63;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import java.util.function.Consumer;

public class Main {

    static Consumer<String> PrintWithIndent(String indent) {
        return (String out) -> System.out.println(indent + out);
    }

    public static void main(String[] args) {
        System.out.println("Hello world!");
        var connectionString = "mongodb://mongo.overcla.im/?ssl=true&keepAlive=true&autoReconnect=true&poolSize=30";
        try (MongoClient client = MongoClients.create(connectionString)) {
            var db = client.getDatabase("birger");
            var after = db.getCollection("after");
            System.out.printf("documents=%d\n", after.countDocuments());
        }
    }
}