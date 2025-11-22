package busbooker.com.view;

import busbooker.com.dao.JadwalDAO;
import busbooker.com.model.User;
import busbooker.com.model.Jadwal;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.List;
import busbooker.com.util.DBConnection;
import busbooker.com.util.HashUtil;
import busbooker.com.dao.PemesananDAO;
import java.util.ArrayList;

public class MainMenu extends JFrame {
    private final User user;
    private final JTable jadwalTable;
    private final DefaultTableModel model;

    // Konstruktor untuk MainMenu dengan parameter user
    public MainMenu(User u) {
        this.user = u;
        setTitle("🚌 BusBooker - Menu Utama");
        setSize(900, 600); // Ukuran tampilan
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Panel Utama (Background)
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(250, 250, 250));

        // Panel Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 102, 204));
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));

        JLabel titleLabel = new JLabel("Dashboard BusBooker");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);

        // Panel Search dan Filter
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));

        // Search Field
        JTextField searchField = new JTextField(20);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.setText("Cari asal & tujuan");

        // Tombol Cari
        JButton searchButton = new JButton("Cari");
        searchButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchButton.setBackground(new Color(0, 102, 204));
        searchButton.setForeground(Color.WHITE);

        JButton pilihBusButton = new JButton("Pilih Bus");
        pilihBusButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        pilihBusButton.setBackground(new Color(0, 102, 204));
        pilihBusButton.setForeground(Color.WHITE);

        // ComboBox untuk Filter Hari
        JComboBox<String> dayComboBox = new JComboBox<>(new String[]{"Senin, 13 Mei 2024", "Selasa, 14 Mei 2024", "Rabu, 15 Mei 2024"});
        dayComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(dayComboBox);

        // Panel Daftar Jadwal
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        String[] columnNames = {"Nama Bus", "Rute", "Jam Keberangkatan", "Harga", "Pilih Bus"};
        model = new DefaultTableModel(columnNames, 0);
        jadwalTable = new JTable(model);
        jadwalTable.setRowHeight(50);
        jadwalTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Mengambil data jadwal dari database
        try {
            List<Jadwal> jadwalList = JadwalDAO.all();  // Mendapatkan data jadwal dari DAO
            for (Jadwal j : jadwalList) {
                
                pilihBusButton.addActionListener(e -> {
                    // Mengarahkan ke PemesananForm dengan User dan Jadwal yang dipilih
                    new PemesananForm(user, j).setVisible(true);
                });

                model.addRow(new Object[] {
                        j.getNamaBus(),  // Menampilkan nama bus yang diambil dari database
                        j.getKeberangkatan() + " → " + j.getTujuan(),
                        j.getWaktu(),
                        "Rp " + j.getHarga(),
                        pilihBusButton
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "❌ Gagal memuat jadwal: " + e.getMessage(),
                    "Kesalahan Database", JOptionPane.ERROR_MESSAGE);
        }

        // Nonaktifkan editor untuk semua kolom selain kolom tombol "Pilih Bus"
jadwalTable.setDefaultEditor(String.class, null); // Menonaktifkan editor untuk kolom selain "Pilih Bus"


        // Menambahkan ButtonColumn untuk merender tombol pada kolom "Pilih Bus"
        // Menambahkan ButtonColumn untuk merender tombol pada kolom "Pilih Bus"
        ButtonColumn buttonColumn = new ButtonColumn(jadwalTable, 4); // Indeks kolom "Pilih Bus" adalah 4


        JScrollPane scrollPane = new JScrollPane(jadwalTable);
        contentPanel.add(scrollPane);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(searchPanel, BorderLayout.CENTER);
        mainPanel.add(contentPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    // Fungsi untuk mengambil data jadwal dari database
    private List<Jadwal> fetchJadwalFromDatabase() {
        List<Jadwal> jadwalList = new ArrayList<>();
        String sql = "SELECT j.id_jadwal, j.id_bus, j.asal, j.tujuan, j.waktu, b.nama_bus, b.harga " +
                     "FROM jadwal j " +
                     "JOIN bus b ON j.id_bus = b.id_bus";  // Join dengan tabel bus untuk nama bus dan harga

        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {  // Eksekusi query

            // Cek apakah data ditemukan
            if (!rs.isBeforeFirst()) {
                JOptionPane.showMessageDialog(this, "Tidak ada jadwal yang tersedia.",
                        "Pemberitahuan", JOptionPane.INFORMATION_MESSAGE);
                return jadwalList;
            }

            // Ambil data dari ResultSet
            while (rs.next()) {
                int id = rs.getInt("id_jadwal");
                String keberangkatan = rs.getString("asal");
                String tujuan = rs.getString("tujuan");
                Timestamp waktu = rs.getTimestamp("waktu");
                String namaBus = rs.getString("nama_bus");  // Nama bus dari tabel bus
                double harga = rs.getDouble("harga");  // Harga bus dari tabel bus

                // Membuat objek Jadwal dengan harga dan menambahkannya ke list
                Jadwal jadwal = new Jadwal(id, rs.getInt("id_bus"), keberangkatan, tujuan, waktu, harga, namaBus);

                // Menambahkan tombol Pilih Bus dengan event listener
                JButton pilihBusButton = new JButton("Pilih Bus");
                pilihBusButton.addActionListener(e -> {
                    // Mengarahkan ke PemesananForm dengan User dan Jadwal yang dipilih
                    new PemesananForm(user, jadwal).setVisible(true);
                });

                // Menambahkan tombol ke dalam list jadwal
                jadwalList.add(jadwal);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "❌ Gagal memuat jadwal: " + e.getMessage(),
                    "Kesalahan Database", JOptionPane.ERROR_MESSAGE);
        }

        return jadwalList;  // Mengembalikan list jadwal yang berhasil diambil
    }

    // Fungsi untuk mengambil harga bus berdasarkan id_bus
    private double getBusHarga(int idBus) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement("SELECT harga FROM bus WHERE id_bus = ?")) {
            stmt.setInt(1, idBus);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("harga");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
