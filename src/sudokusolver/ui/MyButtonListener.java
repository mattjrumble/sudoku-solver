package sudokusolver.ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class MyButtonListener implements ActionListener {

    private JButton button;
    private UserInterface ui;

    public MyButtonListener(UserInterface ui, JButton button) {
        this.ui = ui;
        this.button = button;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String buttonText = button.getText();
        if (buttonText.equals("Solve")) {
            ui.getButton("Reset").setEnabled(false);
            ui.solveReadBoard();
            ui.getButton("Reset").setEnabled(true);
        } else if (buttonText.equals("Reset")) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    ui.getCells()[i][j].setText("");
                }
            }
            ui.getButton("Solve").setEnabled(true);
        }
    }

}
