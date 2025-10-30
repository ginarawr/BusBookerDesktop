package busbooker.com.view;

import javax.swing.*;
import busbooker.com.dao.BusDAO;
import busbooker.com.dao.JadwalDAO;
import busbooker.com.model.Bus;
import busbooker.com.model.Jadwal;
import java.awt.*;
import java.awt.event.*;
import java.sql.Timestamp;
import java.util.List;

public class AdminDashboard extends JFrame {
    public AdminDashboard() {
        setTitle("Admin - Kelola");
        setSize(900,500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        JTabbedPane tabs = new JTabbedPane();

        // Panel Bus
        JPanel pBus = new JPanel(new BorderLayout());
        JPanel addBus = new JPanel(new GridLayout(1,5,6,6));
        var nama = new JTextField(); var kelas = new JTextField(); var harga = new JTextField();
        JButton addB = new JButton("Tambah Bus"); addBus.add(new JLabel("Nama:")); addBus.add(nama); addBus.add(new JLabel("Kelas:")); addBus.add(kelas); addBus.add(harga);
        pBus.add(addBus, BorderLayout.NORTH);
        JPanel busListPanel = new JPanel(); pBus.add(new JScrollPane(busListPanel), BorderLayout.CENTER);
        JButton doAddBus = new JButton("Tambah"); pBus.add(doAddBus, BorderLayout.SOUTH);
        doAddBus.addActionListener(e -> {
            try {
                Bus b = new Bus();
                b.setNama(nama.getText()); b.setKelas(kelas.getText()); b.setHarga(Double.parseDouble(harga.getText()));
                BusDAO.add(b); JOptionPane.showMessageDialog(this,"Bus ditambahkan"); refreshBusList(busListPanel);
            } catch (Exception ex){ ex.printStackTrace(); JOptionPane.showMessageDialog(this,"Error: "+ex.getMessage());}
        });

        // Panel Jadwal
        JPanel pJadwal = new JPanel(new BorderLayout());
        JPanel addJ = new JPanel(new GridLayout(1,6,6,6));
        var idBusFld = new JTextField(); var asalFld = new JTextField(); var tujuanFld = new JTextField(); var waktuFld = new JTextField();
        addJ.add(new JLabel("id_bus:")); addJ.add(idBusFld); addJ.add(new JLabel("Asal:")); addJ.add(asalFld); addJ.add(new JLabel("Tujuan:")); addJ.add(tujuanFld);
        pJadwal.add(addJ, BorderLayout.NORTH);
        JPanel jadwalListPanel = new JPanel(); pJadwal.add(new JScrollPane(jadwalListPanel), BorderLayout.CENTER);
        JButton addJadwalBtn = new JButton("Tambah Jadwal (format: yyyy-MM-dd HH:mm:ss)"); pJadwal.add(addJadwalBtn, BorderLayout.SOUTH);
        addJadwalBtn.addActionListener(e -> {
            try {
                Jadwal j = new Jadwal();
                j.setIdBus(Integer.parseInt(idBusFld.getText()));
                j.setKeberangkatan(asalFld.getText());
                j.setTujuan(tujuanFld.getText());
                j.setWaktu(Timestamp.valueOf(waktuFld.getText()));
                JadwalDAO.add(j); JOptionPane.showMessageDialog(this,"Jadwal ditambahkan"); refreshJadwalList(jadwalListPanel);
            } catch (Exception ex){ ex.printStackTrace(); JOptionPane.showMessageDialog(this,"Error: "+ex.getMessage());}
        });

        tabs.add("Kelola Bus", pBus);
        tabs.add("Kelola Jadwal", pJadwal);
        add(tabs, BorderLayout.CENTER);

        refreshBusList(busListPanel);
        refreshJadwalList(jadwalListPanel);
    }

    private void refreshBusList(JPanel panel) {
        panel.removeAll();
        try {
            java.util.List<busbooker.com.model.Bus> list = BusDAO.all();
            panel.setLayout(new GridLayout(list.size(),1,4,4));
            for (busbooker.com.model.Bus b : list) {
                JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));
                row.add(new JLabel(b.getId()+" - "+b.getNama()+" | "+b.getKelas()+" | "+b.getHarga()));
                JButton del = new JButton("Hapus");
                del.addActionListener(e -> { try { BusDAO.delete(b.getId()); refreshBusList(panel); } catch(Exception ex){ex.printStackTrace();} });
                row.add(del);
                panel.add(row);
            }
        } catch (Exception e){ e.printStackTrace(); }
        panel.revalidate(); panel.repaint();
    }

    private void refreshJadwalList(JPanel panel) {
        panel.removeAll();
        try {
            java.util.List<busbooker.com.model.Jadwal> list = JadwalDAO.all();
            panel.setLayout(new GridLayout(list.size(),1,4,4));
            for (busbooker.com.model.Jadwal j : list) {
                JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));
                row.add(new JLabel(j.getId()+" - busId:"+j.getIdBus()+" | "+j.getKeberangkatan()+" - "+j.getTujuan()+" | "+j.getWaktu()));
                JButton del = new JButton("Hapus");
                del.addActionListener(e -> { try { JadwalDAO.delete(j.getId()); refreshJadwalList(panel); } catch(Exception ex){ex.printStackTrace();} });
                row.add(del);
                panel.add(row);
            }
        } catch (Exception e){ e.printStackTrace(); }
        panel.revalidate(); panel.repaint();
    }
}
