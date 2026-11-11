/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jabaranocoffee.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author kayla
 */

/*
 * Menyediakan koneksi JDBC ke database jabarano_coffee.
 * Setiap pemanggilan getConnection() menghasilkan koneksi baru.
 * Koneksi wajib ditutup oleh pemanggil menggunakan try-with-resources.
 */
public class Koneksi {

    // MAMP default  : port 8889
    // Laragon default: port 3306
    private static final String URL  =
            "jdbc:mysql://localhost:8889/jabarano_coffee" +
            "?useSSL=false&serverTimezone=Asia/Jakarta&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASS = "root"; // MAMP: "root" | Laragon: ""

    // Constructor
    private Koneksi() {
        // Utility class — tidak perlu diinstansiasi
    }

    // ----------------------------------------------------------------
    // Proses: membuat dan mengembalikan koneksi baru
    // ----------------------------------------------------------------
    /*
     * Mengembalikan koneksi JDBC baru ke database.
     * Gunakan di dalam try-with-resources agar koneksi otomatis tertutup.
     *
     * @return Connection yang siap digunakan
     * @throws SQLException jika koneksi gagal
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    // ----------------------------------------------------------------
    // Output: tes koneksi (opsional, untuk debugging)
    // ----------------------------------------------------------------
    /*
     * Menguji koneksi ke database dan mencetak hasilnya ke konsol.
     * Hanya untuk keperluan debugging, tidak dipanggil di production.
     */
    public static void testConnection() {
        try (Connection conn = getConnection()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("[Koneksi] Berhasil terhubung ke database.");
            }
        } catch (SQLException e) {
            System.err.println("[Koneksi] Gagal terhubung: " + e.getMessage());
        }
    }
}