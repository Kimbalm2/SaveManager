import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

public class SaveManagerUI {
    private JFrame mainFrame;
    private JLabel headerLabel;
    private JPanel controlPanel;
    private final UIController uiController;

    public SaveManagerUI(UIController uiController) {
        this.uiController = uiController;
        uiController.setView(this);
        showMainGUI();
    }

    private void prepareMainGUI() {
        mainFrame = new JFrame("Save Manager");
        mainFrame.setSize(400, 200);
        mainFrame.setLayout(new GridLayout(2, 1));

        headerLabel = new JLabel("", JLabel.CENTER);

        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                uiController.saveData();
                System.exit(0);
            }
        });
        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        mainFrame.add(headerLabel);
        mainFrame.add(controlPanel);
        mainFrame.setVisible(true);
    }

    private void showMainGUI() {
        prepareMainGUI();
        headerLabel.setText("What would you like to do?");

        JButton addNewGameBtn = new JButton("Add New Game");
        JButton uploadSavesBtn = new JButton("Upload Saves");
        JButton downloadSavesBtn = new JButton("Download Saves");

        addNewGameBtn.setActionCommand("Add New Game Folder");
        uploadSavesBtn.setActionCommand("Upload Saves");
        downloadSavesBtn.setActionCommand("Download Saves");

        addNewGameBtn.addActionListener(uiController);
        uploadSavesBtn.addActionListener(uiController);
        downloadSavesBtn.addActionListener(uiController);

        controlPanel.add(addNewGameBtn);
        controlPanel.add(uploadSavesBtn);
        controlPanel.add(downloadSavesBtn);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    public void startWindow(){
        mainFrame.dispose();
        showMainGUI();
    }

    private void initializeMainFrame(int rows){
        mainFrame.dispose();
        mainFrame = new JFrame("Save Manager");
        mainFrame.setSize(600, 400);
        mainFrame.setLayout(new GridLayout(rows, 1));
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                uiController.saveData();
                System.exit(0);
            }
        });
    }

    public void showAddWindow(){
        initializeMainFrame(5);
        headerLabel.setText("Enter Game Title Here");
        JTextField textField = new JTextField(30);
        // set up a file picker component
        JFilePicker filePicker = new JFilePicker("Pick a file", "Browse...",JFileChooser.FILES_ONLY);
        filePicker.setMode(JFilePicker.MODE_SAVE);
        mainFrame.add(headerLabel);
        mainFrame.add(textField);
        mainFrame.add(new JLabel("Choose folders or files to save to the cloud here", JLabel.CENTER));
        mainFrame.add(filePicker);
        prepareConfirmAndCancelBtns("Add");
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    public void showUploadWindow() {
        initializeMainFrame(3);
        headerLabel.setText("Select a game save you want to upload.");
        JPanel listPanel = new JPanel();
        JComboBox<String> gameList = new JComboBox<>(uiController.getGameArray());
        uiController.setGameList(gameList);
        listPanel.add(gameList);
        mainFrame.add(headerLabel);
        mainFrame.add(listPanel);
        prepareConfirmAndCancelBtns("Upload");
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }
    //TODO: change the way we get the list of games to get it from the database.
    public void showDownloadWindow() {
        initializeMainFrame(3);
        headerLabel.setText("Select a game save you want to download.");
        JPanel listPanel = new JPanel();
        JComboBox<String> gameList = new JComboBox<>(uiController.getGameArrayFromDB());
        uiController.setGameList(gameList);
        listPanel.add(gameList);
        mainFrame.add(headerLabel);
        mainFrame.add(listPanel);
        prepareConfirmAndCancelBtns("Download");
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    public void showSelectPathWindow(){
        initializeMainFrame(3);
        // set up a file picker component
        JFilePicker filePicker = new JFilePicker("Pick a folder to save file to", "Browse...",JFileChooser.DIRECTORIES_ONLY);
        filePicker.setMode(JFilePicker.MODE_SAVE);
        mainFrame.add(headerLabel);
        mainFrame.add(new JLabel("Choose the folder you want to save the file to", JLabel.CENTER));
        mainFrame.add(filePicker);
        prepareConfirmAndCancelBtns("Confirm");
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    private void prepareConfirmAndCancelBtns (String btnName){
        JButton confirmBtn = new JButton(btnName);
        JButton cancelBtn = new JButton("Cancel");

        confirmBtn.setActionCommand(btnName);
        cancelBtn.setActionCommand("Cancel");

        confirmBtn.addActionListener(uiController);
        cancelBtn.addActionListener(uiController);

        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        controlPanel.add(confirmBtn);
        controlPanel.add(cancelBtn);
        mainFrame.add(controlPanel);
    }
    //TODO: implement multiple file selection?
    public GameEntity getAddData() {
        Date lastModifiedDate = new Date();
        String filePath = "";
        String gameTitle = "";
        String fileName = "";
        for (Component cmp:mainFrame.getContentPane().getComponents()) {
            if (cmp instanceof JFilePicker){
                filePath = ((JFilePicker) cmp).getSelectedFilePath();
                lastModifiedDate = ((JFilePicker) cmp).getModificationDate();
                Path path = Paths.get(filePath);
                fileName = path.getFileName().toString();
            }
            else if(cmp instanceof JTextField){
                gameTitle = ((JTextField) cmp).getText();
            }
        }
        return new GameEntity(gameTitle,filePath,fileName,lastModifiedDate);
    }

    public void showOptionPane(GameEntity tmpGameEntity) {
        int result = JOptionPane.showConfirmDialog(mainFrame,"Upload selected file(s) now?", "Upload selected file(s)?",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if(result == JOptionPane.YES_OPTION){
            uiController.uploadData(tmpGameEntity);
        }
    }

    public void showOptionPane(){
        int result = JOptionPane.showOptionDialog(mainFrame,"File upload successful!","File Upload",JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,null,null,null);
        if (result == 0){
            startWindow();
        }
    }
}
