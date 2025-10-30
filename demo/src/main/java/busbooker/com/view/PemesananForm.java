package busbooker.com.view;

import javax.swing.*;
import busbooker.com.dao.JadwalDAO;
import busbooker.com.dao.PemesananDAO;
import busbooker.com.model.Jadwal;
import busbooker.com.model.Pemesanan;
import busbooker.com.model.User;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class PemesananForm extends JFrame {
    private User user;
    public PemesananForm(User u) {
        this.user = u;
        setTitle("Pemesanan - BusBooker");
        setSize(800,400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Pilih jadwal lalu klik Pesan"));
        add(top, BorderLayout.NORTH);

        JPanel listPanel = new JPanel();
        add(new JScrollPane(listPanel), BorderLayout.CENTER);
        try {
            List<Jadwal> list = JadwalDAO.all();
            listPanel.setLayout(new GridLayout(list.size(),1,4,4));
            for (Jadwal j : list) {
                JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));
                row.add(new JLabel("ID:"+j.getId()+" | Bus:"+j.getIdBus()+" | "+j.getKeberangkatan()+" -> "+j.getTujuan()+" | "+j.getWaktu()));
                JButton book = new JButton("Pesan");
                book.addActionListener(e -> {
                    try {
                        Pemesanan p = new Pemesanan();
                        p.setIdUser(user.getId());
                        p.setIdJadwal(j.getId());
                        p.setStatus("Belum Lunas");
                        boolean ok = PemesananDAO.create(p);
                        if (ok) JOptionPane.showMessageDialog(this,"Pesanan berhasil!");
                        else JOptionPane.showMessageDialog(this,"Gagal pesan");
                    } catch (Exception ex){ ex.printStackTrace(); JOptionPane.showMessageDialog(this,"Error: "+ex.getMessage()); }
                });
                row.add(book);
                listPanel.add(row);
            }
        } catch (Exception e) { e.printStackTrace(); }

    }
}
