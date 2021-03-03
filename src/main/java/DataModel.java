import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class DataModel {
    public enum Window {
        START,
        DOWNLOAD,
        UPLOAD,
        NEW
    }

    private MongoDatabase mongoDatabase;
    private Window window;
    public DataModel() {
        mongoDatabase = getDbConnection();
        //store all Game titles and their filepaths to saved games
        //store the db connection?
        //store UI window state
        Window window = Window.START;
    }

    private MongoDatabase getDbConnection() {
            String uri = getDbConnectionURI();
            MongoClient mongoClient = MongoClients.create(uri);
            return mongoClient.getDatabase("test");
    }

    private static String getDbConnectionURI(){
        Scanner scan = new Scanner("");
        try {
            String path = Objects.requireNonNull(SaveManager.class.getClassLoader().getResource("resources.txt").getPath());
            File resources = new File(path);
            scan = new Scanner(resources);
        }
        catch (IOException e){
            System.exit(0);
        }
        return scan.nextLine();
    }

    public MongoDatabase getMongoDatabase() {
        return mongoDatabase;
    }

    public Window getWindow() {
        return window;
    }

    public void setWindow(Window window) {
        this.window = window;
    }
}
