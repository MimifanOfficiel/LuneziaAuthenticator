package fr.lunezia.luneziaauth.database;

import com.mongodb.*;
import fr.lunezia.luneziaauth.Main;

import java.util.ArrayList;
import java.util.List;

public class Database {

    public static MongoClient mongoClient(String address) {
        ServerAddress addr = new ServerAddress(address, Main.getInstance().getConfig().getInt("database.port"));
        List<MongoCredential> credentials = new ArrayList<>();
        credentials.add(MongoCredential.createCredential(Main.getInstance().getConfig().getString("database.username"), Main.getInstance().getConfig().getString("database.databaseName"), Main.getInstance().getConfig().getString("database.password").toCharArray()));
        return new MongoClient(addr, credentials);
    }

    public static MongoClient client = mongoClient(Main.getInstance().getConfig().getString("database.host"));

    public static DB mongodb = client.getDB(Main.getInstance().getConfig().getString("database.databaseName"));
    public static DBCollection auth_collection = mongodb.getCollection(Main.getInstance().getConfig().getString("database.auth_collection"));

}
