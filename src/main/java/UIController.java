import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class UIController {
    DataModel dataModel;
    SaveManagerUI ui;
    public enum Window {
        START,
        DOWNLOAD,
        UPLOAD,
        ADD
    }
    public UIController(){
        dataModel = DataModel.getInstance();
    }

    private static void dbPractice(){
       
        //MongoClient mongoClient = MongoClients.create(uri);
        //MongoDatabase database = mongoClient.getDatabase("test");
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
        /*GridFSBucket gridFSBucket = GridFSBuckets.create(database,"files");
        GridFSFindIterable files = gridFSBucket.find(eq("metadata.Game", "Dyson Sphere Project"));

        //deleting files
        for (GridFSFile file:files) {
            ObjectId gameId = file.getObjectId();


            System.out.println("Removed file from db.");
        }
        GridFSFindIterable allFiles = gridFSBucket.find();

        for (GridFSFile file:allFiles) {
            System.out.println(file.getMetadata().toJson());
            file.
        }
        */

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

    public void downloadWindow() {
        ui.notify(Window.DOWNLOAD);
    }

    public void uploadWindow() {
        ui.notify(Window.UPLOAD);
    }

    public void newWindow() {
        ui.notify(Window.ADD);
    }

    //handle confirmations and
    public void confirmOrCancel(String command) {
        switch (command){
            case "Cancel" -> ui.notify(Window.START);
            default -> {
                //TODO: Add other cases and logic here
            }
        }
    }

    public void setView(SaveManagerUI ui) {
        this.ui = ui;
    }
}