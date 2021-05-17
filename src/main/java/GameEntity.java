import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Date;

public class GameEntity {
    private final String gameName;
    private final String filePath;
    private final String fileName;
    private final Date lastModifiedDate;
    private ObjectId objectId;

    public GameEntity(String gameName, String filePath, String fileName, Date lastModifiedDate){
        this.gameName = gameName;
        this.filePath = filePath;
        this.fileName = fileName;
        this.lastModifiedDate = lastModifiedDate;
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
                .append("Last Modified Date",lastModifiedDate)
                .append("fileID",objectId)
                .append("key",toString())
                .append("File Path",filePath);
    }

    public String toString(){
        return gameName+":"+fileName;
    }
}
