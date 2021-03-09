
public class GameEntity {
    private final String gameName;
    private final String filePath;

    public GameEntity(String gameName, String filePath){
        this.gameName = gameName;
        this.filePath = filePath;
    }

    public String getGameName() {
        return gameName;
    }

    public String getFilePath() {
        return filePath;
    }
}
