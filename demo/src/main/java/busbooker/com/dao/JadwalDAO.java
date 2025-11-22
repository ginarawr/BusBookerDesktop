package busbooker.com.dao;

import busbooker.com.model.Jadwal;
import busbooker.com.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JadwalDAO {

    // Fungsi untuk mengambil data jadwal + data bus
    public static List<Jadwal> all() throws SQLException {

        String sql =
                "SELECT j.id_jadwal, j.id_bus, j.asal, j.tujuan, j.waktu, b.nama_bus, b.harga " +
                "FROM jadwal j " +
                "JOIN bus b ON j.id_bus = b.id_bus";  // Join dengan tabel bus

        List<Jadwal> list = new ArrayList<>();

        try (Connection c = DBConnection.getConnection();
             PreparedStatement p = c.prepareStatement(sql);
             ResultSet rs = p.executeQuery()) {

            while (rs.next()) {
                // Gunakan constructor yang sesuai untuk membuat objek Jadwal
                Jadwal j = new Jadwal(
                        rs.getInt("id_jadwal"),
                        rs.getInt("id_bus"),
                        rs.getString("asal"),
                        rs.getString("tujuan"),
                        rs.getTimestamp("waktu"),
                        rs.getDouble("harga"),
                        rs.getString("nama_bus") // Menambahkan nama bus
                );
                list.add(j);
            }
        }

        return list;
    }
}
