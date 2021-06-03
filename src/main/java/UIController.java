import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class UIController implements ActionListener {
    private final DataModel dataModel;
    private SaveManagerUI ui;
    private JComboBox<String> gameList;

    public UIController(){
        dataModel = DataModel.getInstance();
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command){
            case "Cancel" -> ui.startWindow();
            case "Add" -> {
                GameEntity tmpGameEntity = ui.getAddData();
                dataModel.addToGameList(tmpGameEntity);
                //TODO: Upload folder contents to mongodb
                ui.showOptionPane(tmpGameEntity);
                ui.startWindow();
            }
            case "Upload" -> {
                int idx = gameList.getSelectedIndex();
                uploadData(dataModel.getGameList().get(idx));
                ui.showOptionPane();
            }
            case "Download" -> {
                if(downloadData((String) gameList.getSelectedItem())){
                    ui.showOptionPane();
                }
            }
            case "Confirm" ->{
                GameEntity tempGameEntity = ui.getAddData();
                String[] gameAndFileName;
                String fileKey = (String) gameList.getSelectedItem();
                if (fileKey != null && fileKey.contains(":"))
                {
                    gameAndFileName = fileKey.split(":");
                    tempGameEntity.setGameName(gameAndFileName[0]);
                    tempGameEntity.setFileName(gameAndFileName[1]);
                    tempGameEntity.setFilePath(tempGameEntity.getFilePath() + "/" + gameAndFileName[1]);
                    tempGameEntity.setLastModifiedDate(new Date(System.currentTimeMillis()));
                    dataModel.addToGameList(tempGameEntity);
                    dataModel.addToLocalGamePathMap(fileKey,tempGameEntity.getFilePath());
                    downloadData(fileKey);
                    ui.showOptionPane();
                }

            }
            case "Add New Game Folder" -> ui.showAddWindow();
            case "Upload Saves" -> ui.showUploadWindow();
            case "Download Saves" -> ui.showDownloadWindow();
        }
    }
    public void uploadData(GameEntity tmpGameEntity){
        dataModel.uploadData(tmpGameEntity);
    }
    public boolean downloadData(String key){
        if(!dataModel.hasPath(key)) {
            ui.showSelectPathWindow();
            return false;
        }
        else {
            dataModel.downloadData(key);
            return true;
        }
    }
    public void saveData(){
        dataModel.saveData();
    }
    public String[] getGameArray(){
        return dataModel.getGameArray();
    }
    public String[] getGameArrayFromDB(){return dataModel.getGameArrayFromDB();}
    public void setView(SaveManagerUI ui) {
        this.ui = ui;
    }
    public void setGameList(JComboBox<String> gameList) {
        this.gameList = gameList;
    }
}