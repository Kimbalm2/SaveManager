import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class mainButtonClickListener implements ActionListener {
    private final UIController uiController;
    public mainButtonClickListener(UIController uiController){
        this.uiController = uiController;
    }

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
