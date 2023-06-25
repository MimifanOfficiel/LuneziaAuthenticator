package fr.lunezia.luneziaauth.database;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import java.util.UUID;

public class DatabaseQueries {

    public static boolean playerExists(UUID uuid){
        return !(Database.auth_collection.findOne(new BasicDBObject("uuid", uuid.toString()))==null);
    }

    public static String getPassword(UUID uuid){
        DBObject r = new BasicDBObject("uuid", uuid.toString());
        DBObject found = Database.auth_collection.findOne(r);
        if(found==null) throw new NullPointerException("The player with uuid " + uuid + " doesn't exists!");
        return (String) found.get("password");
    }

    public static void createPlayer(UUID uuid, String password){
        DBObject user = new BasicDBObject("uuid", uuid.toString());

        user.put("uuid", uuid.toString());
        user.put("password", password);

        Database.auth_collection.insert(user);
    }

    public static void removePlayer(UUID uuid){
        DBObject user = new BasicDBObject("uuid", uuid.toString());
        DBObject found = Database.auth_collection.findOne(user);
        if(found == null) throw new NullPointerException("Le joueur n'existe pas");
        Database.auth_collection.remove(found);
    }


}
