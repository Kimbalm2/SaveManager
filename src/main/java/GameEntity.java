import org.bson.Document;
import org.bson.types.ObjectId;

public class GameEntity {
    private final String gameName;
    private final String filePath;
    private final String fileName;
    private final boolean isFolder;
    private ObjectId objectId;

    public GameEntity(String gameName, String filePath, String fileName, boolean isFolder){
        this.gameName = gameName;
        this.filePath = filePath;
        this.fileName = fileName;
        this.isFolder = isFolder;
    }

    public String getGameName() {
        return gameName;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public boolean isFolder() {
        return isFolder;
    }

    public void setObjectId(ObjectId objectId){
        this. objectId = objectId;
    }

    public ObjectId getObjectId (){
        return objectId;
    }

    public Document toDocument(){
        return new Document()
                .append("Game Title",gameName)
                .append("File Name",fileName)
                .append("isFolder",isFolder)
                .append("fileID",objectId);
    }

    public String toString(){
        return gameName+":"+fileName;
    }
}
