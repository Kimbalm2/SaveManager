import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import org.bson.BsonValue;
import org.bson.Document;
import com.mongodb.Block;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.*;
import com.mongodb.client.gridfs.model.*;
import org.bson.Document;
import org.bson.types.ObjectId;
import java.io.*;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;

import static com.mongodb.client.model.Filters.all;
import static com.mongodb.client.model.Filters.eq;

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
        MongoDatabase database = mongoClient.getDatabase("test");
        //MongoCollection<Document> collection = database.getCollection("");
        //{
            //"Game" : "",
                //"modification date" : "database",
              //  "file" : datastring
        //}
        //Document doc = new Document("Game", "Dyson Sphere Project")
         //       .append("modification date", "mm/dd/yyyy")
           //     .append("file","data");

        // Create a gridFSBucket using the default bucket name "fs"
        //http://mongodb.github.io/mongo-java-driver/4.1/driver/tutorials/gridfs/
        GridFSBucket gridFSBucket = GridFSBuckets.create(database,"files");
        GridFSFindIterable files = gridFSBucket.find(eq("metadata.Game", "Dyson Sphere Project"));

        //deleting files
        /*for (GridFSFile file:files) {
            ObjectId gameId = file.getObjectId();
            gridFSBucket.delete(gameId);
            System.out.println("Removed file from db.");
        }
        GridFSFindIterable allFiles = gridFSBucket.find();

        for (GridFSFile file:allFiles) {
            System.out.println(file.getMetadata().toJson());
        }*/
        // Get the input stream
        //Uploading to gridfs collection
        /*try {
            InputStream streamToUploadFrom = new FileInputStream(new File("dyson.txt"));
            // Create some custom options
            GridFSUploadOptions options = new GridFSUploadOptions()
                    .metadata(new Document("Game", "Dyson Sphere Project"));

            ObjectId fileId = gridFSBucket.uploadFromStream("dyson.txt", streamToUploadFrom, options);
        } catch (FileNotFoundException e){
            // handle exception
        }
        //download the file
        try {
            FileOutputStream streamToDownloadTo = new FileOutputStream("newDyson.txt");
            gridFSBucket.downloadToStream("dyson.txt", streamToDownloadTo);
            streamToDownloadTo.close();
            System.out.println(streamToDownloadTo.toString());
        } catch (IOException e) {
            // handle exception
        }*/
    }

    private static String getDbConnection() throws IOException {
        String path = Objects.requireNonNull(SaveManager.class.getClassLoader().getResource("resources.txt").getPath());
        File resources = new File(path);
        Scanner scan = new Scanner(resources);
        return scan.nextLine();
    }
}
