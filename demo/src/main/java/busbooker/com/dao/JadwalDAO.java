package busbooker.com.dao;

import busbooker.com.util.DBConnection;
import busbooker.com.model.Jadwal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JadwalDAO {
    public static List<Jadwal> all() throws SQLException {
        String sql = "SELECT * FROM jadwal";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement p = c.prepareStatement(sql);
             ResultSet rs = p.executeQuery()) {
            List<Jadwal> list = new ArrayList<>();
            while (rs.next()) {
                Jadwal j = new Jadwal();
                j.setId(rs.getInt("id_jadwal"));
                j.setIdBus(rs.getInt("id_bus"));
                j.setKeberangkatan(rs.getString("asal"));
                j.setTujuan(rs.getString("tujuan"));
                j.setWaktu(rs.getTimestamp("waktu"));
                list.add(j);
            }
            return list;
        }
    }

    public static boolean add(Jadwal j) throws SQLException {
        String sql = "INSERT INTO jadwal (id_bus,asal,tujuan,waktu) VALUES (?,?,?,?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, j.getIdBus());
            p.setString(2, j.getKeberangkatan());
            p.setString(3, j.getTujuan());
            p.setTimestamp(4, j.getWaktu());
            return p.executeUpdate() > 0;
        }
    }

    public static boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM jadwal WHERE id_jadwal = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, id);
            return p.executeUpdate() > 0;
        }
    }
}
