package busbooker.com.view;

import javax.swing.*;
import busbooker.com.model.User;
import java.awt.*;
import java.awt.event.*;

public class MainMenu extends JFrame {
    private User user;
    public MainMenu(User u) {
        this.user = u;
        setTitle("BusBooker - Main");
        setSize(600,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        var panel = new JPanel(new BorderLayout());
        var top = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        top.add(new JLabel("Halo, "+u.getUsername()+" ("+u.getRole()+")"));
        JButton logout = new JButton("Logout");
        top.add(logout);
        panel.add(top, BorderLayout.NORTH);

        var center = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 60));
        JButton bookBtn = new JButton("Pesan Tiket");
        JButton trxBtn = new JButton("Riwayat");
        center.add(bookBtn); center.add(trxBtn);
        if ("admin".equals(u.getRole())) {
            JButton adminBtn = new JButton("Admin Panel");
            center.add(adminBtn);
            adminBtn.addActionListener(e -> new AdminDashboard().setVisible(true));
        }
        panel.add(center, BorderLayout.CENTER);
        add(panel);

        bookBtn.addActionListener(e -> new PemesananForm(user).setVisible(true));
        trxBtn.addActionListener(e -> new TransaksiView(user).setVisible(true));
        logout.addActionListener(e -> { new LoginForm().setVisible(true); dispose(); });
    }
}

