package busbooker.com.dao;

import busbooker.com.util.DBConnection;
import busbooker.com.model.User;
import busbooker.com.util.HashUtil;
import java.sql.*;

public class UserDAO {
    public static boolean register(User u) throws SQLException {
        String sql = "INSERT INTO users (username,email,password,role) VALUES (?,?,?,?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, u.getUsername());
            p.setString(2, u.getEmail());
            p.setString(3, HashUtil.sha256(u.getPassword())); // demo
            p.setString(4, u.getRole() == null ? "user" : u.getRole());
            return p.executeUpdate() > 0;
        }
    }

    public static User login(String email, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, email);
            p.setString(2, HashUtil.sha256(password));
            ResultSet rs = p.executeQuery();
            if (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setUsername(rs.getString("username"));
                u.setEmail(rs.getString("email"));
                u.setRole(rs.getString("role"));
                return u;
            }
            return null;
        }
    }
}

