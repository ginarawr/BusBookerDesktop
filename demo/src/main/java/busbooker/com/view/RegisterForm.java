package busbooker.com.view;

import javax.swing.*;
import busbooker.com.dao.UserDAO;
import busbooker.com.model.User;
import java.awt.*;

public class RegisterForm extends JFrame {
    private JTextField userField, emailField;
    private JPasswordField passField;
    public RegisterForm() {
        setTitle("BusBooker - Register");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(380,260);
        setLocationRelativeTo(null);
        JPanel p = new JPanel(new GridLayout(4,2,8,8));
        p.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        p.add(new JLabel("Username:")); userField = new JTextField(); p.add(userField);
        p.add(new JLabel("Email:")); emailField = new JTextField(); p.add(emailField);
        p.add(new JLabel("Password:")); passField = new JPasswordField(); p.add(passField);
        JButton reg = new JButton("Daftar"); JButton back = new JButton("Kembali");
        p.add(reg); p.add(back);
        add(p);
        reg.addActionListener(e -> {
            try {
                User u = new User();
                u.setUsername(userField.getText());
                u.setEmail(emailField.getText());
                u.setPassword(new String(passField.getPassword()));
                u.setRole("user");
                boolean ok = busbooker.com.dao.UserDAO.register(u);
                if (ok) { JOptionPane.showMessageDialog(this,"Registrasi sukses"); new LoginForm().setVisible(true); dispose(); }
                else JOptionPane.showMessageDialog(this,"Gagal registrasi");
            } catch (Exception ex) { ex.printStackTrace(); JOptionPane.showMessageDialog(this,"Error: "+ex.getMessage()); }
        });
        back.addActionListener(e -> { new LoginForm().setVisible(true); dispose(); });
    }
}
