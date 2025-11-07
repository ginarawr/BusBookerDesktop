package busbooker.com.view;

import javax.swing.*;
import javax.swing.table.*;
import busbooker.com.model.User;
import busbooker.com.model.Jadwal;
import busbooker.com.util.DBConnection;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MainMenu extends JFrame {
    private final User user;
    private final JTable jadwalTable;
    private final DefaultTableModel model;

    public MainMenu(User u) {
        this.user = u;
        setTitle("🚌 BusBooker - Menu Utama");
        setSize(850, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // 🌈 Background gradien lembut
        var panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0,
                        new Color(0, 102, 204),
                        0, getHeight(),
                        new Color(0, 153, 255));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // 🔹 Panel atas (greeting dan tombol)
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        JLabel greetingLabel = new JLabel("Selamat Datang, " + u.getUsername() + " 👋");
        greetingLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        greetingLabel.setForeground(Color.WHITE);
        greetingLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.add(greetingLabel, BorderLayout.WEST);

        // 🔹 Panel tombol kanan atas
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        buttonPanel.setOpaque(false);

        JButton btnPemesanan = new JButton("🚌 Pemesanan Tiket");
        JButton btnRiwayat = new JButton("🎫 Riwayat Transaksi");
        JButton btnLogout = new JButton("🚪 Logout");

        styleButton(btnPemesanan, new Color(0, 153, 255));
        styleButton(btnRiwayat, new Color(0, 200, 150));
        styleButton(btnLogout, new Color(255, 80, 80));

        // Aksi tombol
        btnPemesanan.addActionListener(e -> new PemesananForm(user).setVisible(true));
        btnRiwayat.addActionListener(e -> new TransaksiView(user).setVisible(true));
        btnLogout.addActionListener(e -> {
            new LoginForm().setVisible(true);
            dispose();
        });

        buttonPanel.add(btnPemesanan);
        buttonPanel.add(btnRiwayat);
        buttonPanel.add(btnLogout);
        topPanel.add(buttonPanel, BorderLayout.EAST);

        panel.add(topPanel, BorderLayout.NORTH);

        // 🔹 Panel tengah: tabel jadwal bus
        JPanel center = new JPanel();
        center.setOpaque(false);
        center.setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("📅 Jadwal Bus Tersedia", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(Color.WHITE);
        center.add(title, BorderLayout.NORTH);

        String[] columnNames = {"ID Jadwal", "Keberangkatan", "Tujuan", "Waktu"};
        model = new DefaultTableModel(columnNames, 0);
        jadwalTable = new JTable(model);
        jadwalTable.setRowHeight(28);
        jadwalTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        jadwalTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        jadwalTable.getTableHeader().setBackground(new Color(0, 90, 180));
        jadwalTable.getTableHeader().setForeground(Color.WHITE);

        // Ambil data jadwal
        for (Jadwal j : fetchJadwalFromDatabase()) {
            model.addRow(new Object[]{
                    j.getId(),
                    j.getKeberangkatan(),
                    j.getTujuan(),
                    j.getWaktu()
            });
        }

        JScrollPane scrollPane = new JScrollPane(jadwalTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        center.add(scrollPane, BorderLayout.CENTER);

        panel.add(center, BorderLayout.CENTER);

        add(panel);
    }

    // 🔹 Ambil data jadwal dari database
    private List<Jadwal> fetchJadwalFromDatabase() {
        List<Jadwal> jadwalList = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM jadwal")) {

            while (rs.next()) {
                int id = rs.getInt("id_jadwal");
                int idBus = rs.getInt("id_bus");
                String keberangkatan = rs.getString("asal");
                String tujuan = rs.getString("tujuan");
                Timestamp waktu = rs.getTimestamp("waktu");

                jadwalList.add(new Jadwal(id, idBus, keberangkatan, tujuan, waktu));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "❌ Gagal memuat jadwal: " + e.getMessage(),
                    "Kesalahan Database", JOptionPane.ERROR_MESSAGE);
        }
        return jadwalList;
    }

    // 🔹 Utility: Styling tombol agar seragam
    private void styleButton(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(bg.darker());
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(bg);
            }
        });
    }
}
