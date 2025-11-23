package busbooker.com.view;

import busbooker.com.dao.JadwalDAO;
import busbooker.com.model.User;
import busbooker.com.model.Jadwal;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

public class MainMenu extends JFrame {
    private final User user;
    private final JPanel listPanel;   // Panel tempat card-card jadwal ditampilkan
    private final JTextField asalField;
    private final JTextField tujuanField;

    public MainMenu(User u) {
        this.user = u;

        setTitle("🚌 BusBooker - Pesan Tiket");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ================= HEADER =================
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(0, 102, 204));
        headerPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        // LEFT: Logo + gambar bus
        JPanel leftHeader = new JPanel();
        leftHeader.setLayout(new BoxLayout(leftHeader, BoxLayout.Y_AXIS));
        leftHeader.setOpaque(false);

        JLabel titleLabel = new JLabel("BusBooker");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(Color.WHITE);

        // Tambah subtitel kecil
        JLabel subtitleLabel = new JLabel("Pesan tiket bus dengan cepat dan nyaman");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitleLabel.setForeground(new Color(230, 240, 255));

        // Tambah gambar bus di bawah text
        JLabel busImageLabel = new JLabel();
        busImageLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/assets/busimage.png"));
            Image scaled = icon.getImage().getScaledInstance(120, 60, Image.SCALE_SMOOTH);
            busImageLabel.setIcon(new ImageIcon(scaled));
        } catch (Exception e) {
            // Kalau gambar tidak ketemu, tidak apa-apa, hanya tidak tampil
            e.printStackTrace();
        }

        leftHeader.add(titleLabel);
        leftHeader.add(Box.createVerticalStrut(2));
        leftHeader.add(subtitleLabel);
        leftHeader.add(Box.createVerticalStrut(6));
        leftHeader.add(busImageLabel);

        // RIGHT: sapaan user
        JLabel userLabel = new JLabel("Halo, " + (user != null ? user.getUsername() : "Penumpang"));
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        userLabel.setForeground(Color.WHITE);
        userLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        headerPanel.add(leftHeader, BorderLayout.WEST);
        headerPanel.add(userLabel, BorderLayout.EAST);

        // ================= FILTER / SEARCH BAR =================
        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        filterPanel.setBorder(new EmptyBorder(8, 20, 8, 20));
        filterPanel.setBackground(new Color(245, 245, 245));

        asalField = new JTextField(12);
        asalField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        asalField.setBorder(BorderFactory.createTitledBorder("Asal"));

        tujuanField = new JTextField(12);
        tujuanField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tujuanField.setBorder(BorderFactory.createTitledBorder("Tujuan"));

        JButton searchButton = new JButton("Cari Jadwal");
        searchButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        searchButton.setBackground(new Color(0, 102, 204));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);
        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        filterPanel.add(asalField);
        filterPanel.add(tujuanField);
        filterPanel.add(searchButton);

        // Panel atas gabungkan header + filter
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.add(headerPanel, BorderLayout.NORTH);
        topPanel.add(filterPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);

        // ================= LIST PANEL (CARD-CARD JADWAL) =================
        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(new Color(235, 240, 245));

        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // smooth scrolling

        add(scrollPane, BorderLayout.CENTER);

        // Event tombol Cari
        searchButton.addActionListener(e -> loadJadwal());

        // Pertama kali: tampilkan semua jadwal
        loadJadwal();

        setVisible(true);
    }

    /**
     * Load jadwal dari database dan tampilkan dalam bentuk card.
     * Jika asal / tujuan diisi, bisa difilter (sementara ini simple: filter di sisi Java).
     */
    private void loadJadwal() {
        listPanel.removeAll(); // bersihkan dulu

        String asalFilter = asalField.getText().trim();
        String tujuanFilter = tujuanField.getText().trim();

        try {
            List<Jadwal> jadwalList = JadwalDAO.all();

            if (jadwalList.isEmpty()) {
                JLabel emptyLabel = new JLabel("Belum ada jadwal tersedia.");
                emptyLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
                emptyLabel.setForeground(Color.DARK_GRAY);
                emptyLabel.setBorder(new EmptyBorder(20, 20, 20, 20));
                listPanel.add(emptyLabel);
            } else {
                for (Jadwal j : jadwalList) {
                    // Filter sederhana berdasarkan asal & tujuan (opsional)
                    if (!asalFilter.isEmpty() &&
                            !j.getKeberangkatan().toLowerCase().contains(asalFilter.toLowerCase())) {
                        continue;
                    }
                    if (!tujuanFilter.isEmpty() &&
                            !j.getTujuan().toLowerCase().contains(tujuanFilter.toLowerCase())) {
                        continue;
                    }

                    JPanel card = createJadwalCard(j);
                    listPanel.add(card);
                    listPanel.add(Box.createVerticalStrut(10)); // spasi antar card
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Gagal memuat jadwal: " + e.getMessage(),
                    "Kesalahan Database",
                    JOptionPane.ERROR_MESSAGE);
        }

        listPanel.revalidate();
        listPanel.repaint();
    }

    /**
     * Membuat satu card jadwal (seperti item list di aplikasi tiket online).
     */
    private JPanel createJadwalCard(Jadwal j) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setPreferredSize(new Dimension(900, 100));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        card.setBackground(Color.WHITE);
        card.setBorder(new CompoundBorder(
                new EmptyBorder(5, 20, 5, 20),
                new LineBorder(new Color(220, 220, 220), 1, true)
        ));

        // ========== LEFT: Info Bus & Rute ==========
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setOpaque(false);

        JLabel namaBusLabel = new JLabel(j.getNamaBus() != null ? j.getNamaBus() : "Bus");
        namaBusLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JLabel ruteLabel = new JLabel(j.getKeberangkatan() + " → " + j.getTujuan());
        ruteLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        ruteLabel.setForeground(Color.DARK_GRAY);

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy, HH:mm");
        String waktuStr = (j.getWaktu() != null) ? sdf.format(j.getWaktu()) : "-";
        JLabel waktuLabel = new JLabel("Berangkat: " + waktuStr);
        waktuLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        waktuLabel.setForeground(new Color(80, 80, 80));

        leftPanel.add(namaBusLabel);
        leftPanel.add(Box.createVerticalStrut(5));
        leftPanel.add(ruteLabel);
        leftPanel.add(Box.createVerticalStrut(5));
        leftPanel.add(waktuLabel);

        // ========== CENTER: Info tambahan ==========
        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        JLabel fasilitasLabel = new JLabel("AC • Reclining Seat • Bagasi");
        fasilitasLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        fasilitasLabel.setForeground(new Color(100, 100, 100));

        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(fasilitasLabel);
        centerPanel.add(Box.createVerticalGlue());

        // ========== RIGHT: Harga + Tombol Pilih ==========
        JPanel rightPanel = new JPanel();
        rightPanel.setOpaque(false);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(new EmptyBorder(0, 10, 0, 0));

        String hargaStr = String.format("Rp %,.0f", j.getHarga()).replace(",", ".");
        JLabel hargaLabel = new JLabel(hargaStr);
        hargaLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        hargaLabel.setForeground(new Color(0, 153, 0));
        hargaLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

        JButton pilihButton = new JButton("Pilih");
        pilihButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
        pilihButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        pilihButton.setBackground(new Color(255, 140, 0));
        pilihButton.setForeground(Color.WHITE);
        pilihButton.setFocusPainted(false);
        pilihButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        pilihButton.setPreferredSize(new Dimension(100, 35));
        pilihButton.setMaximumSize(new Dimension(100, 35));

        // Aksi ketika tombol PILIH diklik
        pilihButton.addActionListener(e -> {
            new PemesananForm(user, j).setVisible(true);
        });

        rightPanel.add(Box.createVerticalGlue());
        rightPanel.add(hargaLabel);
        rightPanel.add(Box.createVerticalStrut(8));
        rightPanel.add(pilihButton);
        rightPanel.add(Box.createVerticalGlue());

        card.add(leftPanel, BorderLayout.WEST);
        card.add(centerPanel, BorderLayout.CENTER);
        card.add(rightPanel, BorderLayout.EAST);

        return card;
    }
}
