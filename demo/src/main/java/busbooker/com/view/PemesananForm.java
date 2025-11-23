package busbooker.com.view;

import busbooker.com.model.User;
import busbooker.com.model.Jadwal;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class PemesananForm extends JFrame {
    private final User user;
    private final Jadwal jadwal;

    public PemesananForm(User user, Jadwal jadwal) {
        this.user = user;
        this.jadwal = jadwal;

        setTitle("Pemesanan Tiket Bus");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // ====== HEADER ======
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 102, 204));
        JLabel headerLabel = new JLabel("Pemesanan Tiket");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        headerPanel.add(headerLabel);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // ====== PANEL TENGAH (FORM + QR) ======
        JPanel centerPanel = new JPanel(new BorderLayout());

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(4, 2, 10, 10)); // Rute, Jumlah, Metode, Harga
        formPanel.setBorder(new EmptyBorder(20, 30, 0, 30));

        // Rute
        JLabel jadwalLabel = new JLabel("Jadwal Bus: " + jadwal.getKeberangkatan() + " → " + jadwal.getTujuan());
        jadwalLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        formPanel.add(new JLabel("Rute:"));
        formPanel.add(jadwalLabel);

        // Jumlah Orang
        JLabel jumlahLabel = new JLabel("Jumlah Orang:");
        JTextField jumlahField = new JTextField();
        formPanel.add(jumlahLabel);
        formPanel.add(jumlahField);

        // Metode Pembayaran
        JLabel metodeLabel = new JLabel("Metode Pembayaran:");
        JComboBox<String> metodeComboBox = new JComboBox<>(new String[]{"Tunai", "QR Code"});
        formPanel.add(metodeLabel);
        formPanel.add(metodeComboBox);

        // ====== FORMAT HARGA DI FORM (TANPA .0, PAKAI TITIK RIBUAN) ======
        String hargaFormatted = formatRupiah(jadwal.getHarga());
        JLabel totalHargaLabel = new JLabel("Harga per Tiket: Rp " + hargaFormatted);
        totalHargaLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        formPanel.add(totalHargaLabel);
        formPanel.add(new JLabel("")); // spacer

        // ---- PANEL QR CODE ----
        JPanel qrPanel = new JPanel(new BorderLayout());
        qrPanel.setBorder(new EmptyBorder(10, 30, 20, 30));

        JLabel qrTitle = new JLabel("QR Code (QRIS):");
        qrTitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JLabel qrCodeLabel = new JLabel();
        qrCodeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        qrCodeLabel.setVerticalAlignment(SwingConstants.CENTER);
        qrCodeLabel.setPreferredSize(new Dimension(250, 250));

        try {
            ImageIcon qrCodeImage = new ImageIcon(getClass().getResource("/assets/qrcode.png"));
            Image img = qrCodeImage.getImage().getScaledInstance(220, 220, Image.SCALE_SMOOTH);
            qrCodeLabel.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            e.printStackTrace();
        }

        qrCodeLabel.setVisible(false);

        qrPanel.add(qrTitle, BorderLayout.NORTH);
        qrPanel.add(qrCodeLabel, BorderLayout.CENTER);

        centerPanel.add(formPanel, BorderLayout.NORTH);
        centerPanel.add(qrPanel, BorderLayout.CENTER);

        // Listener comboBox
        metodeComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String metode = (String) e.getItem();
                boolean isQR = "QR Code".equals(metode);
                qrCodeLabel.setVisible(isQR);
                qrPanel.revalidate();
                qrPanel.repaint();
            }
        });

        // ====== BUTTONS ======
        JButton konfirmasiButton = new JButton("Konfirmasi Pemesanan");
        konfirmasiButton.setBackground(new Color(0, 102, 204));
        konfirmasiButton.setForeground(Color.WHITE);
        konfirmasiButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        konfirmasiButton.setFocusPainted(false);
        konfirmasiButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JButton kembaliButton = new JButton("Kembali");
        kembaliButton.setBackground(new Color(255, 80, 80));
        kembaliButton.setForeground(Color.WHITE);
        kembaliButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        kembaliButton.setFocusPainted(false);
        kembaliButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.add(konfirmasiButton);
        buttonPanel.add(kembaliButton);

        konfirmasiButton.addActionListener(e -> {
            String jumlahText = jumlahField.getText().trim();
            String metode = (String) metodeComboBox.getSelectedItem();

            if (jumlahText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Jumlah orang harus diisi!");
                return;
            }

            int jumlah;
            try {
                jumlah = Integer.parseInt(jumlahText);
                if (jumlah <= 0) {
                    JOptionPane.showMessageDialog(this, "Jumlah orang harus lebih dari 0!");
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Jumlah orang harus berupa angka!");
                return;
            }

            if ("QR Code".equals(metode)) {
                JOptionPane.showMessageDialog(this, "Silakan scan QR Code untuk membayar.");
            } else {
                JOptionPane.showMessageDialog(this, "Pesanan Berhasil! Pembayaran Tunai.");
            }

            printTickets(jadwal, jumlah, metode);
            dispose();
        });

        kembaliButton.addActionListener(e -> dispose());

        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    /** Helper untuk format rupiah: 100000 -> 100.000 */
    private static String formatRupiah(double nilai) {
        NumberFormat nf = NumberFormat.getInstance(new Locale("id", "ID"));
        nf.setMaximumFractionDigits(0);
        nf.setMinimumFractionDigits(0);
        return nf.format(nilai);
    }

    /** Cetak tiket */
    private void printTickets(Jadwal jadwal, int jumlah, String metodePembayaran) {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setJobName("Tiket BusBooker");

        Image qrImage = null;
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/assets/qrcode.png"));
            qrImage = icon.getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }

        final Image finalQrImage = qrImage;
        final int totalTiket = jumlah;

        job.setPrintable(new Printable() {
            @Override
            public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                if (pageIndex >= totalTiket) return NO_SUCH_PAGE;

                Graphics2D g2d = (Graphics2D) graphics;
                g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

                double w = pageFormat.getImageableWidth();
                double h = pageFormat.getImageableHeight();

                g2d.setColor(Color.WHITE);
                g2d.fillRect(0, 0, (int) w, (int) h);

                g2d.setColor(Color.BLACK);
                g2d.drawRoundRect(10, 10, (int) w - 20, (int) h - 20, 20, 20);

                int marginLeft = 25;
                int y = 40;

                String title = "TIKET BUSBOOKER";
                g2d.setFont(new Font("SansSerif", Font.BOLD, 18));
                FontMetrics fm = g2d.getFontMetrics();
                int titleWidth = fm.stringWidth(title);
                g2d.drawString(title, (int) (w / 2 - titleWidth / 2), y);
                y += 20;

                g2d.drawLine(marginLeft, y, (int) w - marginLeft, y);
                y += 20;

                SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm");

                g2d.setFont(new Font("Monospaced", Font.PLAIN, 12));
                g2d.drawString("Nama Bus   : " + (jadwal.getNamaBus() != null ? jadwal.getNamaBus() : "-"), marginLeft, y); y += 15;
                g2d.drawString("Rute       : " + jadwal.getKeberangkatan() + " -> " + jadwal.getTujuan(), marginLeft, y); y += 15;
                g2d.drawString("Waktu      : " + sdf.format(jadwal.getWaktu()), marginLeft, y); y += 15;
                g2d.drawString("Metode     : " + metodePembayaran, marginLeft, y); y += 15;

                // ====== FORMAT HARGA DI TIKET CETAK ======
                String hargaCetak = formatRupiah(jadwal.getHarga());
                g2d.drawString("Harga Tiket: Rp " + hargaCetak, marginLeft, y); 
                y += 15;

                g2d.drawString("Tiket ke   : " + (pageIndex + 1) + " dari " + totalTiket, marginLeft, y);
                y += 25;

                g2d.drawLine(marginLeft, y, (int) w - marginLeft, y);
                y += 20;

                g2d.setFont(new Font("SansSerif", Font.ITALIC, 11));
                g2d.drawString("Harap datang 30 menit sebelum keberangkatan.", marginLeft, y); y += 15;
                g2d.drawString("Terima kasih telah menggunakan BusBooker.", marginLeft, y);

                if (finalQrImage != null) {
                    int qrSize = 90;
                    int xQr = (int) w - qrSize - 40;
                    int yQr = (int) h - qrSize - 40;
                    g2d.drawImage(finalQrImage, xQr, yQr, qrSize, qrSize, null);

                    g2d.setFont(new Font("SansSerif", Font.PLAIN, 9));
                    g2d.drawString("Scan untuk cek tiket", xQr, yQr + qrSize + 12);
                }

                return PAGE_EXISTS;
            }
        });

        boolean doPrint = job.printDialog();
        if (doPrint) {
            try {
                job.print();
            } catch (PrinterException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this,
                        "Gagal mencetak tiket: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
