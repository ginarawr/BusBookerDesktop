package busbooker.com.view;

import javax.swing.*;
import busbooker.com.dao.UserDAO;
import busbooker.com.model.User;
import java.awt.*;
import java.awt.event.*;

public class LoginForm extends JFrame {
    private JTextField emailField;
    private JPasswordField passField;
    public LoginForm() {
        setTitle("BusBooker - Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(360,220);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        JPanel p = new JPanel(new GridLayout(3,2,8,8));
        p.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        p.add(new JLabel("Email:")); emailField = new JTextField(); p.add(emailField);
        p.add(new JLabel("Password:")); passField = new JPasswordField(); p.add(passField);
        JButton loginBtn = new JButton("Login");
        JButton regBtn = new JButton("Register");
        p.add(loginBtn); p.add(regBtn);
        add(p, BorderLayout.CENTER);

        loginBtn.addActionListener(e -> {
            String email = emailField.getText();
            String pass = new String(passField.getPassword());
            try {
                User u = UserDAO.login(email, pass);
                if (u != null) {
                    JOptionPane.showMessageDialog(this,"Login sukses. Halo "+u.getUsername());
                    new MainMenu(u).setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this,"Email/password salah");
                }
            } catch (Exception ex) { ex.printStackTrace(); JOptionPane.showMessageDialog(this,"Error: "+ex.getMessage()); }
        });

        regBtn.addActionListener(e -> {
            new RegisterForm().setVisible(true);
            dispose();
        });
    }
}
