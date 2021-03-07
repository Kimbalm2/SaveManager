import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class DataModel {

    private static final DataModel instance = new DataModel();
    private final MongoDatabase mongoDatabase;
    private ArrayList<gameEntity> gameList;

    private SaveManagerUI saveManagerUI;
    private DataModel() {
        mongoDatabase = getDbConnection();
        loadData();
    }

    public static DataModel getInstance(){
        return instance;
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

    public void saveData(){
        Gson gson = new Gson();
        String gameJson = gson.toJson(gameList);
        //write to file
        String currentDir = System.getProperty("user.dir");
        currentDir = currentDir + "\\src\\main\\resources\\gamePaths.json";
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(currentDir);
            byte[] bytes = gameJson.getBytes();
            fileOutputStream.write(bytes);
            fileOutputStream.close();
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    private void loadData(){
        //TODO remove when finished with add new game logic.
        //String founderJson = "[{'gameName': 'Christian','filePath': 1}, {'gameName': 'Marcus', 'filePath': 3}, {'gameName': 'Norman', 'filePath': 2}]";
        String currentDir = System.getProperty("user.dir");
        currentDir = currentDir + "\\src\\main\\resources\\gamePaths.json";
        File jsonFile = new File(currentDir);
        try{
            Scanner scanner = new Scanner(jsonFile);
            String jsonString =scanner.nextLine();
            Gson gson = new Gson();
            Type founderListType = new TypeToken<ArrayList<gameEntity>>(){}.getType();
            gameList = gson.fromJson(jsonString, founderListType);
        }
        catch(IOException ex){
            gameList = new ArrayList<gameEntity>();
        }


    }

    public MongoDatabase getMongoDatabase() {
        return mongoDatabase;
    }

    public ArrayList<gameEntity> getGameList() {
        return gameList;
    }

    public void addToGameList(gameEntity gameEntity) {
        this.gameList.add(gameEntity);
    }

}
