package busbooker.com;

import javax.swing.SwingUtilities;
import busbooker.com.view.AuthFrame;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AuthFrame().setVisible(true));
    }
}
