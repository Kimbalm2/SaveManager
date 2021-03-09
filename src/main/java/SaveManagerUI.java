

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
        mainFrame.setSize(400, 200);
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
            JTextField textField = new JTextField(2);
            TextFieldActionListener textFieldListener= new TextFieldActionListener(textField,uiController);
            textField.addActionListener(textFieldListener);
            textField.addFocusListener(textFieldListener);
            //TODO: file explorer panel
            JFileChooser fc =  new JFileChooser();
            fc.showOpenDialog(mainFrame);
            //TODO: add
            mainFrame.add(headerLabel);
            mainFrame.add(textField);
            mainFrame.add(new JLabel("Choose files to save to the cloud here", JLabel.CENTER));
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


}
