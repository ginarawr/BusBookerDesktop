package busbooker.com;

import javax.swing.SwingUtilities;
import busbooker.com.view.LoginForm;


public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginForm().setVisible(true);
            
        });
    }
}
