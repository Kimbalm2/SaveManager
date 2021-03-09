import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfirmAndCancelButtonClickListener implements ActionListener {
    UIController uiController;
    public ConfirmAndCancelButtonClickListener(UIController uiController){
        this.uiController = uiController;
    }
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        uiController.confirmOrCancel(command);
    }
}