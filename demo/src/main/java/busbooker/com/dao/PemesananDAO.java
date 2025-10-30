package busbooker.com.dao;

import busbooker.com.util.DBConnection;
import busbooker.com.model.Pemesanan;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PemesananDAO {
    public static boolean create(Pemesanan p) throws SQLException {
        String sql = "INSERT INTO pemesanan (id_user,id_jadwal,tanggal_pesan,status_pembayaran) VALUES (?,?,NOW(),?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, p.getIdUser());
            ps.setInt(2, p.getIdJadwal());
            ps.setString(3, p.getStatus() == null ? "Belum Lunas" : p.getStatus());
            return ps.executeUpdate() > 0;
        }
    }

    public static List<Pemesanan> findByUser(int idUser) throws SQLException {
        String sql = "SELECT * FROM pemesanan WHERE id_user = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, idUser);
            ResultSet rs = ps.executeQuery();
            List<Pemesanan> list = new ArrayList<>();
            while (rs.next()) {
                Pemesanan p = new Pemesanan();
                p.setId(rs.getInt("id_pemesanan"));
                p.setIdUser(rs.getInt("id_user"));
                p.setIdJadwal(rs.getInt("id_jadwal"));
                p.setTanggalPesan(rs.getTimestamp("tanggal_pesan"));
                p.setStatus(rs.getString("status_pembayaran"));
                list.add(p);
            }
            return list;
        }
    }
}
