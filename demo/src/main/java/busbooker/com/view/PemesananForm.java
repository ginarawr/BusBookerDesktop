package busbooker.com.view;

import busbooker.com.model.User;
import busbooker.com.model.Jadwal;
import javax.swing.border.EmptyBorder;


import javax.swing.*;
import java.awt.*;

public class PemesananForm extends JFrame {
    private final User user;
    private final Jadwal jadwal;

    // Konstruktor PemesananForm yang menerima parameter User dan Jadwal
    public PemesananForm(User user, Jadwal jadwal) {
        this.user = user;
        this.jadwal = jadwal;

        setTitle("Pemesanan Tiket Bus");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Panel utama untuk form
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(255, 255, 255));

        // Header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 102, 204));
        JLabel headerLabel = new JLabel("Pemesanan Tiket");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        headerPanel.add(headerLabel);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Form panel untuk detail pemesanan
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(new EmptyBorder(20, 30, 20, 30));

        // Informasi Jadwal
        JLabel jadwalLabel = new JLabel("Jadwal Bus: " + jadwal.getKeberangkatan() + " → " + jadwal.getTujuan());
        jadwalLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        formPanel.add(jadwalLabel);

        // Jumlah Orang
        JLabel jumlahLabel = new JLabel("Jumlah Orang:");
        JTextField jumlahField = new JTextField();
        formPanel.add(jumlahLabel);
        formPanel.add(jumlahField);

        // Pilih Metode Pembayaran
        JLabel metodeLabel = new JLabel("Metode Pembayaran:");
        JComboBox<String> metodeComboBox = new JComboBox<>(new String[]{"Tunai", "QR Code"});
        formPanel.add(metodeLabel);
        formPanel.add(metodeComboBox);

        // Panel untuk gambar QR Code (hanya muncul jika memilih "QR Code")
        JLabel qrCodeLabel = new JLabel();
        qrCodeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        qrCodeLabel.setPreferredSize(new Dimension(200, 200));  // Ukuran gambar QR Code

        // Menyembunyikan QR Code pada awalnya
        qrCodeLabel.setVisible(false);

        // Menambahkan gambar QR Code dari file
        try {
            ImageIcon qrCodeImage = new ImageIcon(getClass().getResource("/assets/qrcode.png"));  // Path gambar QR Code
            qrCodeLabel.setIcon(qrCodeImage);
        } catch (Exception e) {
            e.printStackTrace();  // Jika gambar tidak ditemukan
        }

        formPanel.add(qrCodeLabel);

        // Total Harga
        JLabel totalHargaLabel = new JLabel("Total Harga: Rp " + jadwal.getHarga());
        totalHargaLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        formPanel.add(totalHargaLabel);

        // Tombol Konfirmasi Pemesanan
        JButton konfirmasiButton = new JButton("Konfirmasi Pemesanan");
        konfirmasiButton.setBackground(new Color(0, 102, 204));
        konfirmasiButton.setForeground(Color.WHITE);
        konfirmasiButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        konfirmasiButton.setFocusPainted(false);
        konfirmasiButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Tombol Kembali
        JButton kembaliButton = new JButton("Kembali");
        kembaliButton.setBackground(new Color(255, 80, 80));
        kembaliButton.setForeground(Color.WHITE);
        kembaliButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        kembaliButton.setFocusPainted(false);
        kembaliButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Panel untuk tombol konfirmasi dan kembali
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.add(konfirmasiButton);
        buttonPanel.add(kembaliButton);

        // Aksi tombol konfirmasi pemesanan
        konfirmasiButton.addActionListener(e -> {
            String jumlah = jumlahField.getText().trim();
            String metode = (String) metodeComboBox.getSelectedItem();
            if (jumlah.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Jumlah orang harus diisi!");
            } else {
                if ("QR Code".equals(metode)) {
                    // Menampilkan QR Code ketika memilih metode pembayaran QR Code
                    qrCodeLabel.setVisible(true);
                    JOptionPane.showMessageDialog(this, "QR Code telah ditampilkan. Pembayaran QRIS.");
                } else {
                    // Jika tunai, langsung konfirmasi pemesanan
                    JOptionPane.showMessageDialog(this, "Pesanan Berhasil! Pembayaran Tunai.");
                }
                dispose();
            }
        });

        // Aksi tombol kembali
        kembaliButton.addActionListener(e -> {
            dispose();
        });

        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }
}
