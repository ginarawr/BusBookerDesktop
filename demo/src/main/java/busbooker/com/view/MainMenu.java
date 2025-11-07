package busbooker.com.view;

import javax.swing.*;
import busbooker.com.model.User;
import busbooker.com.model.Jadwal;  // Pastikan Anda mengimpor kelas Jadwal
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;  // Untuk mengambil data jadwal

public class MainMenu extends JFrame {
    private User user;

    public MainMenu(User u) {
        this.user = u;
        setTitle("BusBooker - Main");
        setSize(600, 500);  // Memperbesar ukuran agar bisa menampilkan tabel
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Panel utama
        var panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Background Gradient
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(0, 102, 204);
                Color color2 = new Color(51, 204, 255);
                GradientPaint gradient = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel atas (greeting dan logout)
        var top = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        top.setOpaque(false);
        JLabel greetingLabel = new JLabel("Halo, " + u.getUsername() + " (" + u.getRole() + ")");
        greetingLabel.setFont(new Font("Arial", Font.BOLD, 16));
        greetingLabel.setForeground(Color.WHITE);
        top.add(greetingLabel);

        // Tombol Logout
        JButton logout = new JButton("Logout");
        logout.setBackground(new Color(255, 69, 0));
        logout.setForeground(Color.WHITE);
        logout.setFocusPainted(false);
        logout.setFont(new Font("Arial", Font.BOLD, 12));
        logout.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        logout.setCursor(new Cursor(Cursor.HAND_CURSOR));
        top.add(logout);
        panel.add(top, BorderLayout.NORTH);

        // Panel tengah (Menampilkan Jadwal Bus)
        var center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setOpaque(false);

        // Membuat tabel untuk Jadwal Bus
        String[] columnNames = {"ID Jadwal", "Keberangkatan", "Tujuan", "Waktu", "Tombol Pesan"};
        Object[][] data = getJadwalData();  // Mengambil data jadwal (akan dijelaskan di bawah)
        JTable jadwalTable = new JTable(data, columnNames);
        jadwalTable.setPreferredScrollableViewportSize(new Dimension(550, 200));
        jadwalTable.setFillsViewportHeight(true);
        jadwalTable.setBackground(new Color(255, 255, 255));

        // Menambahkan JScrollPane untuk membuat tabel bisa digulir
        JScrollPane scrollPane = new JScrollPane(jadwalTable);
        center.add(scrollPane);
        
        panel.add(center, BorderLayout.CENTER);
        add(panel);

        // Action listeners
        logout.addActionListener(e -> {
            new LoginForm().setVisible(true);
            dispose();
        });
    }

    // Method untuk mendapatkan data jadwal bus (harus Anda sesuaikan dengan model dan database)
    private Object[][] getJadwalData() {
        List<Jadwal> jadwalList = fetchJadwalFromDatabase();  // Ambil data jadwal dari database
        Object[][] data = new Object[jadwalList.size()][5];
        
        for (int i = 0; i < jadwalList.size(); i++) {
            Jadwal j = jadwalList.get(i);
            data[i][0] = j.getId();  // ID Jadwal
            data[i][1] = j.getKeberangkatan();  // Keberangkatan
            data[i][2] = j.getTujuan();  // Tujuan
            data[i][3] = j.getWaktu();  // Waktu
            data[i][4] = new JButton("Pesan Tiket");  // Tombol Pesan
        }

        return data;
    }

    // Method untuk mengambil jadwal dari database (harus Anda sesuaikan)
    private List<Jadwal> fetchJadwalFromDatabase() {
        List<Jadwal> jadwalList = new ArrayList<>();
        // Implementasi untuk mengambil data jadwal dari database menggunakan JDBC atau ORM
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/busbooker", "root", "password");
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM jadwal")) {
            
            while (rs.next()) {
                int id = rs.getInt("id_jadwal");
                int idBus = rs.getInt("id_bus");
                String keberangkatan = rs.getString("asal");
                String tujuan = rs.getString("tujuan");
                Timestamp waktu = rs.getTimestamp("waktu");
                jadwalList.add(new Jadwal(id, idBus, asal, tujuan, waktu));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jadwalList;
    }

}
