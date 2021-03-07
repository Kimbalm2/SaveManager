import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SaveManagerUI {
    private JFrame mainFrame;
    private JLabel headerLabel;
    private JPanel controlPanel;
    private UIController uiController;

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

        addNewGameBtn.addActionListener(new mainButtonClickListener());
        uploadSavesBtn.addActionListener(new mainButtonClickListener());
        downloadSavesBtn.addActionListener(new mainButtonClickListener());

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
        prepareNewWindow(3,1);
        prepareConfirmAndCancelBtns("Add");
    }

    private void prepareNewWindow(int rows,int cols){
        mainFrame.dispose();
        mainFrame = new JFrame("Save Manager");
        mainFrame.setSize(400, 200);
        mainFrame.setLayout(new GridLayout(rows, cols));
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        if(rows == 3){
            //TODO: change to text entry box for game title
            headerLabel.setText("Enter Game Title Here");
            //TODO: file explorer panel

            //TODO: add
            mainFrame.add(headerLabel);
            mainFrame.add(new JLabel("Choose files to save to the cloud here", JLabel.CENTER));
        }
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
        JButton confirmBtn = new JButton(btnName);
        JButton cancelBtn = new JButton("Cancel");

        confirmBtn.setActionCommand(btnName);
        cancelBtn.setActionCommand("Cancel");

        confirmBtn.addActionListener(new confrimAndCancelButtonClickListener());
        cancelBtn.addActionListener(new confrimAndCancelButtonClickListener());

        controlPanel.add(confirmBtn);
        controlPanel.add(cancelBtn);
    }
    private class mainButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (command.equals("Add New Game Folder")) {
                uiController.newWindow();
            } else if (command.equals("Upload Saves")) {
                uiController.uploadWindow();
            } else {
                uiController.downloadWindow();
            }
        }
    }

    private class confrimAndCancelButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            uiController.confirmOrCancel(command);
        }
    }

}
