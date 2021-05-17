import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
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
    private final MongoDatabase mongoDatabase;
    private ArrayList<GameEntity> gameList;
    //(gameName:fileName) -> filePath
    private HashMap<String,String> localGamePathMap;
    private GridFSBucket gridFSBucket;
    private SaveManagerUI saveManagerUI;
    private DataModel() {
        mongoDatabase = getDbConnection();
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
        //Uploading to gridfs collection
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
    //TODO: Think about the flow of this logic -> find the game key I already found from the gridFS in getGameArrayFromDB????? can we simplify this?
    public void downloadData(String gameKey){
        GridFSFindIterable gridFSFile = gridFSBucket.find(eq("metadata.key", gameKey));
        //TODO:
        //write file to selected folder if match
        //if no match need user to select folder where file is located.
        //once it's selected then write file to folder
        //if no match also add to JSON
        //Not found in DB - what should we do?
        if(gridFSFile.first() == null){

        }
        //Found in db
        else {
            //try and do a key lookup to the hash map
                //if found then write to file path - after prompting them to do so. We will need some new preview windows and stuff here
                //else we need to prompt them to select a file path to download the file to - similar to add game window logic.
        }

    }
    //TODO: resolve warning
    public String[] getGameArrayFromDB(){
        ArrayList<String> dbGameList = new ArrayList<>();
        gridFSBucket.find()
                .forEach(gridFSFile -> dbGameList.add(gridFSFile.getMetadata().get("key").toString()));
        String[] array = dbGameList.toArray(new String[dbGameList.size()]);
        return array;
    }
    //TODO
    public GameEntity getDBEntity(String key){
        return gameList.get(0);
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

}
