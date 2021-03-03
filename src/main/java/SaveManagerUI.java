import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SaveManagerUI {
    private JFrame mainFrame;
    private JLabel headerLabel;
    private JLabel statusLabel;
    private JPanel controlPanel;
    private UIController uiController;

    public SaveManagerUI(UIController uiController) {
        this.uiController = uiController;
        prepareMainGUI();
        showMainGUI();
    }

    private void prepareMainGUI() {
        mainFrame = new JFrame("Save Manager");
        mainFrame.setSize(400, 400);
        mainFrame.setLayout(new GridLayout(3, 1));

        headerLabel = new JLabel("", JLabel.CENTER);
        statusLabel = new JLabel("", JLabel.CENTER);
        statusLabel.setSize(350, 100);

        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        mainFrame.add(headerLabel);
        mainFrame.add(controlPanel);
        mainFrame.add(statusLabel);
        mainFrame.setVisible(true);
    }

    private void showMainGUI() {
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

        mainFrame.setVisible(true);
    }

    private class mainButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (command.equals("Add New Game")) {
                uiController.newWindow();
            } else if (command.equals("Upload Saves")) {
                uiController.uploadWindow();
            } else {
                uiController.downloadWindow();
            }
        }

    }


}
