import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Date;

public class GameEntity {
    private String gameName;
    private String filePath;
    private String fileName;
    private Date lastModifiedDate;
    private ObjectId objectId;

    public GameEntity(String gameName, String filePath, String fileName, Date lastModifiedDate){
        this.gameName = gameName;
        this.filePath = filePath;
        this.fileName = fileName;
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public String getGameName() {
        return gameName;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public ObjectId getObjectId (){
        return objectId;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public void setObjectId(ObjectId objectId){
        this. objectId = objectId;
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
