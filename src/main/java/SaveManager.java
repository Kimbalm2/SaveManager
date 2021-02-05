import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class SaveManager {
    public static void main (String[] args){
        String uri = "";
        try{
            uri = getDbConnection();
        }
        catch (IOException e){
            System.exit(0);
        }
        MongoClient mongoClient = MongoClients.create(uri);
        MongoDatabase database = mongoClient.getDatabase("local");
        MongoCollection<Document> collection = database.getCollection("");
        //{
            //"Game" : "",
                //"modification date" : "database",
              //  "file" : datastring
        //}
        Document doc = new Document("Game", "Dyson Sphere Project")
                .append("modification date", "mm/dd/yyyy")
                .append("file","data");


    }

    private static String getDbConnection() throws IOException {
        String path = Objects.requireNonNull(SaveManager.class.getClassLoader().getResource("resources.txt").getPath());
        File resources = new File(path);
        Scanner scan = new Scanner(resources);
        return scan.nextLine();
    }
}
