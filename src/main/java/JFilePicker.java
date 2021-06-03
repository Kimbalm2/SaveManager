import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Date;

//https://www.codejava.net/java-se/swing/file-picker-component-in-swing
public class JFilePicker extends JPanel {
    private final JTextField textField;
    private final JFileChooser fileChooser;
    private int mode;
    public static final int MODE_OPEN = 1;
    public static final int MODE_SAVE = 2;


    public JFilePicker(String textFieldLabel, String buttonLabel, int selectionMode) {

        fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(selectionMode);
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        // creates the GUI
        JLabel label = new JLabel(textFieldLabel);

        textField = new JTextField(30);
        JButton button = new JButton(buttonLabel);

        button.addActionListener(this::buttonActionPerformed);

        add(label);
        add(textField);
        add(button);

    }
    private void buttonActionPerformed(ActionEvent evt) {
        if (mode == MODE_OPEN) {
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                textField.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        } else if (mode == MODE_SAVE) {
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                textField.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        }
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public String getSelectedFilePath() {
        return textField.getText();
    }

    public Date getModificationDate (){
        return new Date(fileChooser.getSelectedFile().lastModified());
    }
}
