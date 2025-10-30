package busbooker.com.dao;


import busbooker.com.util.DBConnection;
import busbooker.com.model.Bus;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BusDAO {
    public static List<Bus> all() throws SQLException {
        String sql = "SELECT * FROM bus";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement p = c.prepareStatement(sql);
             ResultSet rs = p.executeQuery()) {
            List<Bus> list = new ArrayList<>();
            while (rs.next()) {
                Bus b = new Bus();
                b.setId(rs.getInt("id_bus"));
                b.setNama(rs.getString("nama_bus"));
                b.setKelas(rs.getString("kelas"));
                b.setHarga(rs.getDouble("harga"));
                list.add(b);
            }
            return list;
        }
    }

    public static boolean add(Bus b) throws SQLException {
        String sql = "INSERT INTO bus (nama_bus,kelas,harga) VALUES (?,?,?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, b.getNama());
            p.setString(2, b.getKelas());
            p.setDouble(3, b.getHarga());
            return p.executeUpdate() > 0;
        }
    }

    public static boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM bus WHERE id_bus = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, id);
            return p.executeUpdate() > 0;
        }
    }
}

