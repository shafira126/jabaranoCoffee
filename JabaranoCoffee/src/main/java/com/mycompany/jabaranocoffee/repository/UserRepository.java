/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jabaranocoffee.repository;

import com.mycompany.jabaranocoffee.entity.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 *
 * @author kayla
 */

/*
 * Mengelola data akun staff dan proses login.
 */
public class UserRepository {

    // ----------------------------------------------------------------
    // Constructor
    // ----------------------------------------------------------------
    public UserRepository() {}

    // ----------------------------------------------------------------
    // Input: menerima username dan password dari FormLogin
    // ----------------------------------------------------------------

    // ----------------------------------------------------------------
    // Proses: validasi login ke database
    // ----------------------------------------------------------------
    /*
     * Memvalidasi kombinasi username dan password.
     * Mengembalikan objek User jika berhasil, null jika gagal.
     *
     * @param username username staff
     * @param password password staff (plain text)
     * @return User jika valid, null jika tidak ditemukan
     */
    public User login(String username, String password) {
        // Query mencari user dengan username dan password yang cocok
        String sql = "SELECT id, username, password, created_at " +
                     "FROM users " +
                     "WHERE username = ? AND password = ?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Isi parameter query
            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // ------------------------------------------------
                    // Output: kembalikan User jika ditemukan
                    // ------------------------------------------------
                    return mapResultSetToUser(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("[UserRepository] Error login: " + e.getMessage());
        }

        return null; // Login gagal
    }

    /*
     * Mencari user berdasarkan username saja.
     * Berguna untuk pengecekan keberadaan akun.
     *
     * @param username username yang dicari
     * @return User jika ditemukan, null jika tidak ada
     */
    public User findByUsername(String username) {
        String sql = "SELECT id, username, password, created_at " +
                     "FROM users WHERE username = ?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUser(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("[UserRepository] Error findByUsername: " + e.getMessage());
        }

        return null;
    }

    // ----------------------------------------------------------------
    // Helper: mapping ResultSet -> User
    // ----------------------------------------------------------------
    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));

        Timestamp ts = rs.getTimestamp("created_at");
        if (ts != null) {
            user.setCreatedAt(ts.toLocalDateTime());
        }

        return user;
    }
}