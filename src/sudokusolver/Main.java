package sudokusolver;

import javax.swing.SwingUtilities;
import sudokusolver.ui.UserInterface;

public class Main {

    public static void main(String[] args) {
        
        UserInterface ui = new UserInterface();
        SwingUtilities.invokeLater(ui);
        

    }
    
}
