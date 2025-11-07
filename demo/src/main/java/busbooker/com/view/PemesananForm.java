package busbooker.com.view;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.List;
import busbooker.com.dao.JadwalDAO;
import busbooker.com.dao.PemesananDAO;
import busbooker.com.model.Jadwal;
import busbooker.com.model.Pemesanan;
import busbooker.com.model.User;

public class PemesananForm extends JFrame {
    private final User user;

    public PemesananForm(User u) {
        this.user = u;

        // 🔹 Frame dasar
        setTitle("🚌 Pemesanan Tiket - BusBooker");
        setSize(850, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // 🔹 Header (judul atas)
        JLabel header = new JLabel("Daftar Jadwal Bus", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 20));
        header.setForeground(new Color(30, 60, 90));
        header.setBorder(new EmptyBorder(20, 10, 10, 10));
        add(header, BorderLayout.NORTH);

        // 🔹 Panel daftar jadwal
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        add(scrollPane, BorderLayout.CENTER);

        // 🔹 Footer info
        JLabel infoLabel = new JLabel("Klik tombol 'Pesan' untuk melakukan pemesanan tiket.", SwingConstants.CENTER);
        infoLabel.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        infoLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(infoLabel, BorderLayout.SOUTH);

        // 🔹 Ambil data jadwal dari database
        try {
            List<Jadwal> list = JadwalDAO.all();

            if (list.isEmpty()) {
                JLabel kosong = new JLabel("Tidak ada jadwal tersedia saat ini.", SwingConstants.CENTER);
                kosong.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                listPanel.add(kosong);
            } else {
                for (Jadwal j : list) {
                    // Panel tiap jadwal
                    JPanel card = new JPanel(new BorderLayout());
                    card.setBackground(new Color(250, 250, 255));
                    card.setBorder(new CompoundBorder(
                            new LineBorder(new Color(220, 220, 240), 1, true),
                            new EmptyBorder(10, 15, 10, 15)
                    ));

                    // Info jadwal (kiri)
                    String detail = "<html><b>ID:</b> " + j.getId() +
                            " &nbsp;&nbsp; <b>Bus:</b> " + j.getIdBus() +
                            "<br><b>Rute:</b> " + j.getKeberangkatan() + " → " + j.getTujuan() +
                            "<br><b>Waktu:</b> " + j.getWaktu() + "</html>";
                    JLabel label = new JLabel(detail);
                    label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                    card.add(label, BorderLayout.CENTER);

                    // Tombol pesan (kanan)
                    JButton book = new JButton("Pesan");
                    book.setBackground(new Color(0, 102, 204));
                    book.setForeground(Color.WHITE);
                    book.setFocusPainted(false);
                    book.setFont(new Font("Segoe UI", Font.BOLD, 13));
                    book.setBorder(new EmptyBorder(8, 20, 8, 20));
                    book.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

                    // Efek hover
                    book.addMouseListener(new java.awt.event.MouseAdapter() {
                        @Override
                        public void mouseEntered(java.awt.event.MouseEvent evt) {
                            book.setBackground(new Color(0, 80, 180));
                        }

                        @Override
                        public void mouseExited(java.awt.event.MouseEvent evt) {
                            book.setBackground(new Color(0, 102, 204));
                        }
                    });

                    // Aksi pesan
                    book.addActionListener(e -> {
                        try {
                            Pemesanan p = new Pemesanan();
                            p.setIdUser(user.getId());
                            p.setIdJadwal(j.getId());
                            p.setStatus("Belum Lunas");

                            boolean ok = PemesananDAO.create(p);
                            if (ok) {
                                JOptionPane.showMessageDialog(this,
                                        "✅ Pesanan berhasil dibuat!",
                                        "Berhasil", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(this,
                                        "Gagal membuat pesanan.",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(this,
                                    "Terjadi kesalahan: " + ex.getMessage(),
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    });

                    card.add(book, BorderLayout.EAST);
                    listPanel.add(card);
                    listPanel.add(Box.createVerticalStrut(8));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Gagal memuat jadwal: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
