import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import org.bson.Document;
import org.bson.types.ObjectId;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;
import static com.mongodb.client.model.Filters.eq;

public class DataModel {

    private static final DataModel instance = new DataModel();
    private ArrayList<GameEntity> gameList;
    //(gameName:fileName) -> filePath
    private HashMap<String,String> localGamePathMap;
    private final GridFSBucket gridFSBucket;


    private DataModel() {
        MongoDatabase mongoDatabase = getDbConnection();
        gridFSBucket = GridFSBuckets.create(mongoDatabase,"files");
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
        catch (Exception e){
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
        String currentDir = System.getProperty("user.dir");
        localGamePathMap = new HashMap<>();
        currentDir = currentDir + "\\src\\main\\resources\\gamePaths.json";
        File jsonFile = new File(currentDir);
        try{
            Scanner scanner = new Scanner(jsonFile);
            String jsonString =scanner.nextLine();
            Gson gson = new Gson();
            Type founderListType = new TypeToken<ArrayList<GameEntity>>(){}.getType();
            gameList = gson.fromJson(jsonString, founderListType);
            for (GameEntity game:gameList) {
               localGamePathMap.put(game.toString(),game.getFilePath());
            }
        }
        catch(IOException ex){
            gameList = new ArrayList<>();
        }
    }

    public void uploadData(GameEntity gameEntity){
        try {
            gridFSBucket.delete(gameEntity.getObjectId());
            InputStream streamToUploadFrom = new FileInputStream(gameEntity.getFilePath());
            // Create some custom options
            GridFSUploadOptions options = new GridFSUploadOptions()
                    .metadata(gameEntity.toDocument());
            ObjectId fileId = gridFSBucket.uploadFromStream(gameEntity.getFileName(), streamToUploadFrom, options);
            gameEntity.setObjectId(fileId);
        } catch (FileNotFoundException e){
            // handle exception
            //TODO: add dialog feedback on failure.
            System.out.printf("ERROR: Game: %S failed to upload",gameEntity.getGameName());
        }
    }
    public void downloadData(String gameKey){
        GridFSFindIterable gridFSFindIterable = gridFSBucket.find(eq("metadata.key", gameKey));
        FileOutputStream streamToDownloadTo;
        //we found a game file with the game key gameName:fileName
        GridFSFile gridFSFile = gridFSFindIterable.first();
        if (gridFSFile != null){
            try {
                streamToDownloadTo = new FileOutputStream(localGamePathMap.get(gameKey));
                gridFSBucket.downloadToStream(gridFSFile.getObjectId(), streamToDownloadTo);
                streamToDownloadTo.close();
            } catch (IOException e) {
                //TODO notify user
                e.printStackTrace();
            }
        }
        //Not found in DB - what should we do? Will this ever happen? All possible gameKeys would be from the db already.
    }
    
    public String[] getGameArrayFromDB(){
        ArrayList<String> dbGameList = new ArrayList<>();
        String[] gameArray;
        //for every game file stored, get the key we use to map to the file paths in local storage.
        gridFSBucket.find()
                .forEach(gridFSFile ->
                {
                    Document metaData = gridFSFile.getMetadata();
                    if (metaData != null && metaData.containsKey("key")){
                        dbGameList.add(metaData.get("key").toString());
                    }
                });
        gameArray = new String[dbGameList.size()];
        return dbGameList.toArray(gameArray);
    }

    public ArrayList<GameEntity> getGameList() {
        return gameList;
    }

    public String[] getGameArray(){
        String[] array = new String[gameList.size()];
        for(int i = 0; i < array.length; i++) {
            array[i] = gameList.get(i).toString();
        }
        return array;
    }

    public void addToGameList(GameEntity gameEntity) {
        this.gameList.add(gameEntity);
    }

    public void addToLocalGamePathMap (String key, String filePath){
        localGamePathMap.put(key,filePath);
    }

    public boolean hasPath (String key){
        return localGamePathMap.containsKey(key);
    }

}
