package busbooker.com.view;

import javax.swing.*;
import busbooker.com.dao.PemesananDAO;
import busbooker.com.model.Pemesanan;
import busbooker.com.model.User;
import java.awt.*;
import java.util.List;

public class TransaksiView extends JFrame {
    private User user;
    public TransaksiView(User u) {
        this.user = u;
        setTitle("Riwayat Transaksi");
        setSize(700,400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        JPanel panel = new JPanel();
        add(new JScrollPane(panel), BorderLayout.CENTER);
        try {
            List<Pemesanan> list = PemesananDAO.findByUser(user.getId());
            panel.setLayout(new GridLayout(list.size(),1,4,4));
            for (Pemesanan p : list) {
                JPanel r = new JPanel(new FlowLayout(FlowLayout.LEFT));
                r.add(new JLabel("ID:"+p.getId()+" | Jadwal:"+p.getIdJadwal()+" | Tgl:"+p.getTanggalPesan()+" | Status:"+p.getStatus()));
                panel.add(r);
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
}
