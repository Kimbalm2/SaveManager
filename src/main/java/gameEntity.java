public class gameEntity {
    private final String gameName;
    private final String filePath;

    public gameEntity(String gameName, String filePath){
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
