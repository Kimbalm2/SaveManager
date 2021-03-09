import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class TextFieldActionListener implements ActionListener, FocusListener {
    private final JTextField textField;
    private final UIController uiController;
    public TextFieldActionListener(JTextField textField, UIController uiController) {
        this.textField = textField;
        this.uiController = uiController;
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        uiController.setTmpGameTitle(textField.getText());
    }

    /**
     * Invoked when a component gains the keyboard focus.
     *
     * @param e the event to be processed
     */
    @Override
    public void focusGained(FocusEvent e) {

    }

    /**
     * Invoked when a component loses the keyboard focus.
     *
     * @param e the event to be processed
     */
    @Override
    public void focusLost(FocusEvent e) {
        uiController.setTmpGameTitle(textField.getText());
    }
}
