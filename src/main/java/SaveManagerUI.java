

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Path;
import java.nio.file.Paths;

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

        addNewGameBtn.addActionListener(new mainButtonClickListener(uiController));
        uploadSavesBtn.addActionListener(new mainButtonClickListener(uiController));
        downloadSavesBtn.addActionListener(new mainButtonClickListener(uiController));

        controlPanel.add(addNewGameBtn);
        controlPanel.add(uploadSavesBtn);
        controlPanel.add(downloadSavesBtn);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }


    public void notify(UIController.Window window){
        switch (window){
            case START -> {
                mainFrame.dispose();
                showMainGUI();
            }
            case ADD -> showAddWindow();
            case UPLOAD -> showUploadWindow();
            case DOWNLOAD -> showDownloadWindow();
        }
    }

    private void showAddWindow() {
        prepareNewWindow(5,1);
        prepareConfirmAndCancelBtns("Add");
    }

    private void prepareNewWindow(int rows,int cols){
        mainFrame.dispose();
        mainFrame = new JFrame("Save Manager");
        mainFrame.setSize(600, 400);
        mainFrame.setLayout(new GridLayout(rows, cols));
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                uiController.saveData();
                System.exit(0);
            }
        });
        //The only panel with 3 rows is add new game
        if(rows == 5){
            headerLabel.setText("Enter Game Title Here");
            JTextField textField = new JTextField(30);
            // set up a file picker component
            JFilePicker filePicker = new JFilePicker("Pick a file", "Browse...");
            filePicker.setMode(JFilePicker.MODE_SAVE);
            controlPanel = new JPanel();
            controlPanel.setLayout(new FlowLayout());
            mainFrame.add(headerLabel);
            mainFrame.add(textField);
            mainFrame.add(new JLabel("Choose folders or files to save to the cloud here", JLabel.CENTER));
            mainFrame.add(filePicker);
        }
        //Upload or download windows
        else if(rows == 2){
            //TODO: Add a selecting panel linked with game title data from model
            mainFrame.add(new JLabel("Choose game title here", JLabel.CENTER));
        }
        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        mainFrame.add(controlPanel);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    private void showUploadWindow() {
        prepareNewWindow(2,1);
        prepareConfirmAndCancelBtns("Upload");
    }

    private void showDownloadWindow() {
        prepareNewWindow(2,1);
        prepareConfirmAndCancelBtns("Download");
    }

    private void prepareConfirmAndCancelBtns (String btnName){
        ConfirmAndCancelButtonClickListener buttonClickListener = new ConfirmAndCancelButtonClickListener(uiController);
        JButton confirmBtn = new JButton(btnName);
        JButton cancelBtn = new JButton("Cancel");

        confirmBtn.setActionCommand(btnName);
        cancelBtn.setActionCommand("Cancel");

        confirmBtn.addActionListener(buttonClickListener);
        cancelBtn.addActionListener(buttonClickListener);

        controlPanel.add(confirmBtn);
        controlPanel.add(cancelBtn);
    }
    //TODO: implement multiple file selection?
    public GameEntity getAddData() {
        String filePath = "";
        String gameTitle = "";
        String fileName = "";
        boolean isFolder = true;
        for (Component cmp:mainFrame.getContentPane().getComponents()) {
            if (cmp instanceof JFilePicker){
                filePath = ((JFilePicker) cmp).getSelectedFilePath();
                Path path = Paths.get(filePath);
                fileName = path.getFileName().toString();
                if (fileName.contains(".")){
                    isFolder = false;
                }
            }
            else if(cmp instanceof JTextField){
                gameTitle = ((JTextField) cmp).getText();
            }
        }
        return new GameEntity(gameTitle,filePath,fileName,isFolder);
    }

    public void showOptionPane(GameEntity tmpGameEntity) {
        int result = JOptionPane.showConfirmDialog(mainFrame,"Upload selected file(s) now?", "Upload selected file(s)?",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if(result == JOptionPane.YES_OPTION){
            uiController.uploadData(tmpGameEntity);
        }
    }
}
